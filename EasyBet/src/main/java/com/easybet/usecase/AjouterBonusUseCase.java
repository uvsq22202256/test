package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.entity.Transaction;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.domain.repository.TransactionRepository;
import com.easybet.infrastructure.event.PortefeuilleBonusAjouteEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

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

    @Transactional
    public Transaction execute(Long joueurId, BigDecimal montant, String typeBonus, String codePromo) {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être supérieur à 0");
        }

        Portefeuille portefeuille = portefeuilleRepository.findByJoueurId(joueurId)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé"));

        // --- LOGIQUE DE SÉPARATION CORRIGÉE ---
        BigDecimal ancienSolde;
        BigDecimal nouveauSolde;
        String categorie;

        // Si c'est un dépôt -> Solde Réel
        if ("DEPOT".equalsIgnoreCase(typeBonus) || "DEPOT_CLIENT".equalsIgnoreCase(typeBonus)) {
            ancienSolde = portefeuille.getSoldeReel();
            nouveauSolde = ancienSolde.add(montant);
            portefeuille.setSoldeReel(nouveauSolde);
            categorie = "DEPOT";
        }
        // Sinon -> Solde Bonus
        else {
            ancienSolde = portefeuille.getSoldeBonus();
            nouveauSolde = ancienSolde.add(montant);
            portefeuille.setSoldeBonus(nouveauSolde);
            categorie = "BONUS";
        }

        portefeuille.setDateModification(LocalDateTime.now());

        // Création de la transaction
        Transaction transaction = new Transaction(
                portefeuille.getId(),
                categorie,
                montant,
                ancienSolde,
                nouveauSolde
        );
        transaction.setReference(categorie + "-" + UUID.randomUUID().toString());
        transaction.setDescription(typeBonus + (codePromo != null ? " (" + codePromo + ")" : ""));

        // Sauvegardes
        transaction = transactionRepository.save(transaction);
        portefeuilleRepository.save(portefeuille);

        // Kafka
        PortefeuilleBonusAjouteEvent event = new PortefeuilleBonusAjouteEvent(
                portefeuille.getId(), joueurId, portefeuille.getPseudo(),
                montant, nouveauSolde, typeBonus, codePromo, LocalDateTime.now()
        );
        kafkaTemplate.send("portefeuille-bonus-ajoute", event);

        return transaction;
    }
}