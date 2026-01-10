package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.entity.Transaction;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.domain.repository.TransactionRepository;
import com.easybet.infrastructure.event.PortefeuilleTransfertEffectueEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Use Case - Transférer des fonds entre deux portefeuilles
 */
@Service
public class TransfertFondsUseCase {

    private final PortefeuilleRepository portefeuilleRepository;
    private final TransactionRepository transactionRepository;
    private final KafkaTemplate<String, PortefeuilleTransfertEffectueEvent> kafkaTemplate;

    public TransfertFondsUseCase(PortefeuilleRepository portefeuilleRepository,
                                TransactionRepository transactionRepository,
                                KafkaTemplate<String, PortefeuilleTransfertEffectueEvent> kafkaTemplate) {
        this.portefeuilleRepository = portefeuilleRepository;
        this.transactionRepository = transactionRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Transaction execute(Long joueurSourceId, Long joueurDestinataireId, BigDecimal montant) {
        // Valider le montant
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Le montant doit être supérieur à 0");
        }

        // Récupérer les portefeuilles
        Portefeuille portefeuilleSource = portefeuilleRepository.findByJoueurId(joueurSourceId)
                .orElseThrow(() -> new RuntimeException("Portefeuille source non trouvé"));

        Portefeuille portefeuilleDestinataire = portefeuilleRepository.findByJoueurId(joueurDestinataireId)
                .orElseThrow(() -> new RuntimeException("Portefeuille destinataire non trouvé"));

        // Vérifier les statuts
        if (!"ACTIF".equals(portefeuilleSource.getStatut())) {
            throw new RuntimeException("Le portefeuille source n'est pas actif");
        }
        if (!"ACTIF".equals(portefeuilleDestinataire.getStatut())) {
            throw new RuntimeException("Le portefeuille destinataire n'est pas actif");
        }

        // Vérifier le solde suffisant
        if (portefeuilleSource.getSoldeReel().compareTo(montant) < 0) {
            throw new RuntimeException("Solde insuffisant. Solde disponible : " + portefeuilleSource.getSoldeReel());
        }

        // Calculer les nouveaux soldes
        BigDecimal ancienSoldeSource = portefeuilleSource.getSoldeReel();
        BigDecimal nouveauSoldeSource = ancienSoldeSource.subtract(montant);

        BigDecimal ancienSoldeDestinataire = portefeuilleDestinataire.getSoldeReel();
        BigDecimal nouveauSoldeDestinataire = ancienSoldeDestinataire.add(montant);

        // Créer la transaction
        Transaction transaction = new Transaction(
            portefeuilleSource.getId(),
            "TRANSFERT",
            montant,
            ancienSoldeSource,
            nouveauSoldeSource
        );
        transaction.setPortefeuilleDestinataireId(portefeuilleDestinataire.getId());
        transaction.setReference("TRANS-" + UUID.randomUUID().toString());
        transaction.setMethodePayement("PORTEFEUILLE");

        // Sauvegarder la transaction
        transaction = transactionRepository.save(transaction);

        // Mettre à jour les portefeuilles
        portefeuilleSource.setSoldeReel(nouveauSoldeSource);
        portefeuilleSource.setDateModification(LocalDateTime.now());
        portefeuilleRepository.save(portefeuilleSource);

        portefeuilleDestinataire.setSoldeReel(nouveauSoldeDestinataire);
        portefeuilleDestinataire.setDateModification(LocalDateTime.now());
        portefeuilleRepository.save(portefeuilleDestinataire);

        // Publier l'événement Kafka
        PortefeuilleTransfertEffectueEvent event = new PortefeuilleTransfertEffectueEvent(
            portefeuilleSource.getId(),
            portefeuilleSource.getJoueurId(),
            portefeuilleSource.getPseudo(),
            portefeuilleDestinataire.getId(),
            portefeuilleDestinataire.getJoueurId(),
            portefeuilleDestinataire.getPseudo(),
            montant,
            nouveauSoldeSource,
            nouveauSoldeDestinataire,
            LocalDateTime.now()
        );
        kafkaTemplate.send("portefeuille-transfert-effectue", event);

        return transaction;
    }
}

