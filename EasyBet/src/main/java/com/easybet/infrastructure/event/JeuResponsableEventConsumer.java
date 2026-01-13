package com.easybet.infrastructure.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JeuResponsableEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(JeuResponsableEventConsumer.class);

    @KafkaListener(topics = "limite-depot-definie-event", groupId = "easybet-group")
    public void consumeLimiteDefinie(LimiteDepotDefinieEvent event) {
        log.info("JEU RESPONSABLE [LimiteDefinie] -> Joueur: {}, Montant: {}", event.getJoueurId(), event.getMontantLimite());
    }

    @KafkaListener(topics = "limite-depot-modifiee-event", groupId = "easybet-group")
    public void consumeLimiteModifiee(LimiteDepotModifieeEvent event) {
        log.info("JEU RESPONSABLE [LimiteModifiee] -> Joueur: {}, Changement: {} -> {}", event.getJoueurId(), event.getAncienMontant(), event.getNouveauMontant());
    }

    @KafkaListener(topics = "limite-depot-supprimee-event", groupId = "easybet-group")
    public void consumeLimiteSupprimee(LimiteDepotSupprimeeEvent event) {
        log.info("JEU RESPONSABLE [LimiteSupprimee] -> Joueur: {}, La limite a été levée.", event.getJoueurId());
    }
}