package com.easybet.infrastructure.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Producer Kafka pour les événements de joueurs
 * Infrastructure Layer
 */
@Component
public class JoueurEventProducer {

    private static final Logger log = LoggerFactory.getLogger(JoueurEventProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public JoueurEventProducer(@Autowired(required = false) KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Publie un événement de création de joueur
     */
    public void publishJoueurCreeEvent(Long joueurId, String pseudo, String email) {
        if (kafkaTemplate == null) {
            log.warn("KafkaTemplate absent, publication de l'evenement ignoree (joueurId={})", joueurId);
            return;
        }
        JoueurCreeEvent event = JoueurCreeEvent.builder()
                .joueurId(joueurId)
                .pseudo(pseudo)
                .email(email)
                .timestamp(LocalDateTime.now())
                .build();

        // Envoyer avec une clé (joueurId) pour stabiliser l'ordre et faciliter l'idempotence
        kafkaTemplate.send("joueur-cree-event", joueurId.toString(), event);
        log.info("Evenement publie : joueur-cree-event pour joueurId={}", joueurId);
    }

    /**
     * Publie un événement de suppression de joueur
     */
    public void publishJoueurSupprimeEvent(Long joueurId) {
        if (kafkaTemplate == null) {
            log.warn("KafkaTemplate absent, publication de l'evenement ignoree (joueurId={})", joueurId);
            return;
        }
        JoueurSupprimeEvent event = JoueurSupprimeEvent.builder()
                .joueurId(joueurId)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send("joueur-supprime-event", joueurId.toString(), event);
        log.info("Evenement publie : joueur-supprime-event pour joueurId={}", joueurId);
    }
}
