package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.entity.Transaction;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.domain.repository.TransactionRepository;
import com.easybet.infrastructure.event.PortefeuilleBonusAjouteEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Use Case - Ajouter un bonus à un portefeuille
 */
@Service
public class AjouterBonusUseCase {

    private final PortefeuilleRepository portefeuilleRepository;
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, PortefeuilleBonusAjouteEvent> kafkaTemplate;

    public AjouterBonusUseCase(PortefeuilleRepository portefeuilleRepository,
                              TransactionRepository transactionRepository,
                              KafkaTemplate<String, PortefeuilleBonusAjouteEvent> kafkaTemplate) {
        this.portefeuilleRepository = portefeuilleRepository;
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Transaction execute(Long joueurId, BigDecimal montant, String typeBonus, String codePromo) {
        // Valider le montant
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant du bonus doit être supérieur à 0");
        }

        // Récupérer le portefeuille
        Portefeuille portefeuille = portefeuilleRepository.findByJoueurId(joueurId)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé pour le joueur : " + joueurId));

        // Vérifier le statut
        if (!"ACTIF".equals(portefeuille.getStatut())) {
            throw new RuntimeException("Le portefeuille n'est pas actif");
        }

        // Créer la transaction
        BigDecimal ancienSolde = portefeuille.getSoldeBonus();
        BigDecimal nouveauSolde = ancienSolde.add(montant);

        Transaction transaction = new Transaction(
            portefeuille.getId(),
            "BONUS",
            montant,
            ancienSolde,
            nouveauSolde
        );
        transaction.setReference("BONUS-" + UUID.randomUUID().toString());
        transaction.setDescription("Bonus : " + typeBonus + (codePromo != null ? " (" + codePromo + ")" : ""));

        // Sauvegarder la transaction
        transaction = transactionRepository.save(transaction);

        // Mettre à jour le portefeuille
        portefeuille.setSoldeBonus(nouveauSolde);
        portefeuille.setDateModification(LocalDateTime.now());
        portefeuilleRepository.save(portefeuille);

        // Publier l'événement Kafka
        PortefeuilleBonusAjouteEvent event = new PortefeuilleBonusAjouteEvent(
            portefeuille.getId(),
            portefeuille.getJoueurId(),
            portefeuille.getPseudo(),
            montant,
            nouveauSolde,
            typeBonus,
            codePromo,
            LocalDateTime.now()
        );
        kafkaTemplate.send("portefeuille-bonus-ajoute", event);

        return transaction;
    }
}

