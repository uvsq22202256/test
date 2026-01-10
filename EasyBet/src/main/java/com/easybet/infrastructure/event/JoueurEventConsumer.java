package com.easybet.infrastructure.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumer Kafka pour les événements de joueurs
 * Infrastructure Layer
 */
@Component
public class JoueurEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(JoueurEventConsumer.class);

    /**
     * Consomme les événements de création de joueur
     */
    @KafkaListener(topics = "joueur-cree-event", groupId = "easybet-group", containerFactory = "joueurCreeKafkaListenerFactory")
    public void consumeJoueurCreeEvent(JoueurCreeEvent event) {
        logger.info("Événement reçu : Nouveau joueur créé - ID: {}, Pseudo: {}, Email: {}, Timestamp: {}",
                event.getJoueurId(),
                event.getPseudo(),
                event.getEmail(),
                event.getTimestamp());

        // Ici vous pouvez ajouter la logique métier
        // Par exemple : envoyer un email de bienvenue, créer un bonus, etc.
    }

    /**
     * Consomme les événements de suppression de joueur
     */
    @KafkaListener(topics = "joueur-supprime-event", groupId = "easybet-group", containerFactory = "joueurSupprimeKafkaListenerFactory")
    public void consumeJoueurSupprimeEvent(JoueurSupprimeEvent event) {
        logger.info("Événement reçu : Joueur supprimé - ID: {}, Timestamp: {}",
                event.getJoueurId(),
                event.getTimestamp());

        // Ici vous pouvez ajouter la logique métier
        // Par exemple : archiver les données, envoyer une notification, etc.
    }
}
