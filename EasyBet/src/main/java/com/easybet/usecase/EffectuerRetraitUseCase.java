package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.entity.Transaction;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.domain.repository.TransactionRepository;
import com.easybet.infrastructure.event.PortefeuilleRetraitEffectueEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Use Case - Effectuer un retrait sur un portefeuille
 */
@Service
public class EffectuerRetraitUseCase {

    private final PortefeuilleRepository portefeuilleRepository;
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, PortefeuilleRetraitEffectueEvent> kafkaTemplate;

    public EffectuerRetraitUseCase(PortefeuilleRepository portefeuilleRepository,
                                  TransactionRepository transactionRepository,
                                  KafkaTemplate<String, PortefeuilleRetraitEffectueEvent> kafkaTemplate) {
        this.portefeuilleRepository = portefeuilleRepository;
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Transaction execute(Long joueurId, BigDecimal montant, String methodePayement, String reference) {
        // Valider le montant
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être supérieur à 0");
        }

        // Récupérer le portefeuille
        Portefeuille portefeuille = portefeuilleRepository.findByJoueurId(joueurId)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé pour le joueur : " + joueurId));

        // Vérifier le statut
        if (!"ACTIF".equals(portefeuille.getStatut())) {
            throw new RuntimeException("Le portefeuille n'est pas actif");
        }

        // Vérifier le solde suffisant
        if (portefeuille.getSoldeReel().compareTo(montant) < 0) {
            throw new RuntimeException("Solde insuffisant. Solde disponible : " + portefeuille.getSoldeReel());
        }

        // Créer la transaction
        BigDecimal ancienSolde = portefeuille.getSoldeReel();
        BigDecimal nouveauSolde = ancienSolde.subtract(montant);

        Transaction transaction = new Transaction(
            portefeuille.getId(),
            "RETRAIT",
            montant,
            ancienSolde,
            nouveauSolde
        );
        transaction.setMethodePayement(methodePayement);
        transaction.setReference(reference != null ? reference : "RET-" + UUID.randomUUID().toString());

        // Sauvegarder la transaction
        transaction = transactionRepository.save(transaction);

        // Mettre à jour le portefeuille
        portefeuille.setSoldeReel(nouveauSolde);
        portefeuille.setDateModification(LocalDateTime.now());
        portefeuilleRepository.save(portefeuille);

        // Publier l'événement Kafka
        PortefeuilleRetraitEffectueEvent event = new PortefeuilleRetraitEffectueEvent(
            portefeuille.getId(),
            portefeuille.getJoueurId(),
            portefeuille.getPseudo(),
            montant,
            nouveauSolde,
            LocalDateTime.now(),
            transaction.getReference()
        );
        kafkaTemplate.send("portefeuille-retrait-effectue", event);

        return transaction;
    }
}

