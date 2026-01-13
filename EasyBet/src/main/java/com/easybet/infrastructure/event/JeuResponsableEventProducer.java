package com.easybet.infrastructure.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Component
public class JeuResponsableEventProducer {

    private static final Logger log = LoggerFactory.getLogger(JeuResponsableEventProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public JeuResponsableEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishLimiteDefinie(String limiteId, String joueurId, BigDecimal montant) {
        LimiteDepotDefinieEvent event = LimiteDepotDefinieEvent.builder()
                .limiteId(limiteId).joueurId(joueurId).montantLimite(montant).timestamp(LocalDateTime.now()).build();
        kafkaTemplate.send("limite-depot-definie-event", joueurId, event);
        log.info("Publication event LimiteDepotDefinieEvent : joueur {}", joueurId);
    }

    public void publishLimiteModifiee(String limiteId, String joueurId, BigDecimal ancien, BigDecimal nouveau) {
        LimiteDepotModifieeEvent event = LimiteDepotModifieeEvent.builder()
                .limiteId(limiteId).joueurId(joueurId).ancienMontant(ancien).nouveauMontant(nouveau).timestamp(LocalDateTime.now()).build();
        kafkaTemplate.send("limite-depot-modifiee-event", joueurId, event);
        log.info("Publication event LimiteDepotModifieeEvent : joueur {}", joueurId);
    }

    public void publishLimiteSupprimee(String limiteId, String joueurId) {
        LimiteDepotSupprimeeEvent event = LimiteDepotSupprimeeEvent.builder()
                .limiteId(limiteId).joueurId(joueurId).timestamp(LocalDateTime.now()).build();
        kafkaTemplate.send("limite-depot-supprimee-event", joueurId, event);
        log.info("Publication event LimiteDepotSupprimeeEvent : joueur {}", joueurId);
    }
}