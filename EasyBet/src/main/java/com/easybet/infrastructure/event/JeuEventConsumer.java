package com.easybet.infrastructure.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JeuEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(JeuEventConsumer.class);

    @KafkaListener(topics = "jeu-cree-event", groupId = "easybet-group")
    public void consumeJeuCree(JeuCreeEvent event) {
        log.info("Event reçu [JeuCree] -> ID: {}, Nom: {}", event.getId(), event.getNom());
    }

    @KafkaListener(topics = "jeu-modifie-event", groupId = "easybet-group")
    public void consumeJeuModifie(JeuModifieEvent event) {
        log.info("Event reçu [JeuModifie] -> ID: {}, Nom changé: {} -> {}",
                event.getId(), event.getAncienNom(), event.getNouveauNom());
    }

    @KafkaListener(topics = "jeu-supprime-event", groupId = "easybet-group")
    public void consumeJeuSupprime(JeuSupprimeEvent event) {
        log.info("Event reçu [JeuSupprime] -> ID: {}", event.getId());
    }

    @KafkaListener(topics = "session-demarree-event", groupId = "easybet-group")
    public void consumeSessionDemarree(SessionDemarreeEvent event) {
        log.info("Event reçu [SessionDemarree] -> Session: {}, Joueur: {}, Mise: {}",
                event.getSessionId(), event.getJoueurId(), event.getMise());
    }

    @KafkaListener(topics = "session-terminee-event", groupId = "easybet-group")
    public void consumeSessionTerminee(SessionTermineeEvent event) {
        log.info("Event reçu [SessionTerminee] -> Session: {}, Gain: {}",
                event.getSessionId(), event.getGain());
    }
}