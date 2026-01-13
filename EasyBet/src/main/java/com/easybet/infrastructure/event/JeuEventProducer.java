package com.easybet.infrastructure.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Component
public class JeuEventProducer {

    private static final Logger log = LoggerFactory.getLogger(JeuEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public JeuEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishJeuCree(String id, String nom, String type, BigDecimal rtp) {
        JeuCreeEvent event = JeuCreeEvent.builder()
                .id(id).nom(nom).type(type).rtp(rtp).timestamp(LocalDateTime.now()).build();
        kafkaTemplate.send("jeu-cree-event", id, event);
        log.info("Publication event JeuCreeEvent : {}", id);
    }

    public void publishJeuModifie(String id, String ancienNom, String nouveauNom, BigDecimal nouveauRtp) {
        JeuModifieEvent event = JeuModifieEvent.builder()
                .id(id)
                .ancienNom(ancienNom)
                .nouveauNom(nouveauNom)
                .nouveauRtp(nouveauRtp)
                .timestamp(LocalDateTime.now())
                .build();
        kafkaTemplate.send("jeu-modifie-event", id, event);
        log.info("Publication event JeuModifieEvent : {}", id);
    }

    // --- AJOUT POUR LA SUPPRESSION ---
    public void publishJeuSupprime(String id) {
        JeuSupprimeEvent event = JeuSupprimeEvent.builder()
                .id(id).timestamp(LocalDateTime.now()).build();
        kafkaTemplate.send("jeu-supprime-event", id, event);
        log.info("Publication event JeuSupprimeEvent : {}", id);
    }

    // Méthodes session (déjà présentes normalement)
    public void publishSessionDemarree(String sessionId, String joueurId, String jeuId, BigDecimal mise) {
        SessionDemarreeEvent event = SessionDemarreeEvent.builder()
                .sessionId(sessionId).joueurId(joueurId).jeuId(jeuId).mise(mise).timestamp(LocalDateTime.now()).build();
        kafkaTemplate.send("session-demarree-event", sessionId, event);
        log.info("Publication event SessionDemarreeEvent : {}", sessionId);
    }

    public void publishSessionTerminee(String sessionId, String joueurId, BigDecimal gain) {
        SessionTermineeEvent event = SessionTermineeEvent.builder()
                .sessionId(sessionId).joueurId(joueurId).gain(gain).timestamp(LocalDateTime.now()).build();
        kafkaTemplate.send("session-terminee-event", sessionId, event);
        log.info("Publication event SessionTermineeEvent : {}", sessionId);
    }
}