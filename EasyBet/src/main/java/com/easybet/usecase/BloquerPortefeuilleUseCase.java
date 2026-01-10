package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.infrastructure.event.PortefeuilleBlockeEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Use Case - Bloquer un portefeuille
 */
@Service
public class BloquerPortefeuilleUseCase {

    private final PortefeuilleRepository portefeuilleRepository;
    private final KafkaTemplate<String, PortefeuilleBlockeEvent> kafkaTemplate;

    public BloquerPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository,
                                     KafkaTemplate<String, PortefeuilleBlockeEvent> kafkaTemplate) {
        this.portefeuilleRepository = portefeuilleRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Portefeuille execute(Long portefeuilleId, String raison, LocalDateTime dateDeblocage) {
        Portefeuille portefeuille = portefeuilleRepository.findById(portefeuilleId)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé avec l'ID : " + portefeuilleId));

        portefeuille.setStatut("BLOQUE");
        portefeuille.setDateModification(LocalDateTime.now());
        portefeuille = portefeuilleRepository.save(portefeuille);

        // Publier l'événement Kafka
        PortefeuilleBlockeEvent event = new PortefeuilleBlockeEvent(
            portefeuille.getId(),
            portefeuille.getJoueurId(),
            portefeuille.getPseudo(),
            raison,
            LocalDateTime.now(),
            dateDeblocage
        );
        kafkaTemplate.send("portefeuille-bloque", event);

        return portefeuille;
    }
}

