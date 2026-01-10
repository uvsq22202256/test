package com.easybet.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration Kafka pour Event-Driven Architecture
 * Infrastructure Layer
 */
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:easybet-group}")
    private String groupId;

    // ========== PRODUCER CONFIGURATION ==========

    @Bean
    public ProducerFactory<String, Object> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.springframework.kafka.support.serializer.JsonSerializer");
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    // ========== KAFKA TEMPLATES POUR PORTEFEUILLE ==========

    @Bean
    public KafkaTemplate<String, com.easybet.infrastructure.event.PortefeuilleDepotEffectueEvent>
            portefeuilleDepotKafkaTemplate() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.springframework.kafka.support.serializer.JsonSerializer");
        DefaultKafkaProducerFactory<String, com.easybet.infrastructure.event.PortefeuilleDepotEffectueEvent> factory =
                new DefaultKafkaProducerFactory<>(config);
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, com.easybet.infrastructure.event.PortefeuilleRetraitEffectueEvent>
            portefeuilleRetraitKafkaTemplate() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.springframework.kafka.support.serializer.JsonSerializer");
        DefaultKafkaProducerFactory<String, com.easybet.infrastructure.event.PortefeuilleRetraitEffectueEvent> factory =
                new DefaultKafkaProducerFactory<>(config);
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, com.easybet.infrastructure.event.PortefeuilleBonusAjouteEvent>
            portefeuilleBonusKafkaTemplate() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.springframework.kafka.support.serializer.JsonSerializer");
        DefaultKafkaProducerFactory<String, com.easybet.infrastructure.event.PortefeuilleBonusAjouteEvent> factory =
                new DefaultKafkaProducerFactory<>(config);
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, com.easybet.infrastructure.event.PortefeuilleTransfertEffectueEvent>
            portefeuilleTransfertKafkaTemplate() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.springframework.kafka.support.serializer.JsonSerializer");
        DefaultKafkaProducerFactory<String, com.easybet.infrastructure.event.PortefeuilleTransfertEffectueEvent> factory =
                new DefaultKafkaProducerFactory<>(config);
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public KafkaTemplate<String, com.easybet.infrastructure.event.PortefeuilleBlockeEvent>
            portefeuilleBlockeKafkaTemplate() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                "org.springframework.kafka.support.serializer.JsonSerializer");
        DefaultKafkaProducerFactory<String, com.easybet.infrastructure.event.PortefeuilleBlockeEvent> factory =
                new DefaultKafkaProducerFactory<>(config);
        return new KafkaTemplate<>(factory);
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
                "org.springframework.kafka.support.serializer.JsonDeserializer");
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put("spring.json.trusted.packages", "*");
        // Si les en-têtes ne sont pas présents, le listener spécifique prendra le relai
        config.put("spring.json.use.type.headers", true);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    // ========== CONSUMER CONFIGURATION (spécifique type) ==========

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, com.easybet.infrastructure.event.JoueurCreeEvent>
            joueurCreeKafkaListenerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
                "org.springframework.kafka.support.serializer.JsonDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("spring.json.trusted.packages", "*");
        // Ne pas dépendre des en-têtes; fixer le type cible
        props.put("spring.json.use.type.headers", false);
        props.put("spring.json.value.default.type",
                "com.easybet.infrastructure.event.JoueurCreeEvent");

        DefaultKafkaConsumerFactory<String, com.easybet.infrastructure.event.JoueurCreeEvent> cf =
                new DefaultKafkaConsumerFactory<>(props);
        ConcurrentKafkaListenerContainerFactory<String, com.easybet.infrastructure.event.JoueurCreeEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, com.easybet.infrastructure.event.JoueurSupprimeEvent>
            joueurSupprimeKafkaListenerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS,
                "org.springframework.kafka.support.serializer.JsonDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("spring.json.trusted.packages", "*");
        props.put("spring.json.use.type.headers", false);
        props.put("spring.json.value.default.type",
                "com.easybet.infrastructure.event.JoueurSupprimeEvent");

        DefaultKafkaConsumerFactory<String, com.easybet.infrastructure.event.JoueurSupprimeEvent> cf =
                new DefaultKafkaConsumerFactory<>(props);
        ConcurrentKafkaListenerContainerFactory<String, com.easybet.infrastructure.event.JoueurSupprimeEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        return factory;
    }

    // ========== TOPICS CONFIGURATION ==========

    @Bean
    public NewTopic joueurCreeTopic() {
        return TopicBuilder.name("joueur-cree-event")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic joueurSupprimeTopic() {
        return TopicBuilder.name("joueur-supprime-event")
                .partitions(1)
                .replicas(1)
                .build();
    }

    // ========== TOPICS PORTEFEUILLE ==========

    @Bean
    public NewTopic portefeuilleDepotTopic() {
        return TopicBuilder.name("portefeuille-depot-effectue")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic portefeuilleRetraitTopic() {
        return TopicBuilder.name("portefeuille-retrait-effectue")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic portefeuilleBonusTopic() {
        return TopicBuilder.name("portefeuille-bonus-ajoute")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic portefeuilleTransfertTopic() {
        return TopicBuilder.name("portefeuille-transfert-effectue")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic portefeuilleBlockeTopic() {
        return TopicBuilder.name("portefeuille-bloque")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
