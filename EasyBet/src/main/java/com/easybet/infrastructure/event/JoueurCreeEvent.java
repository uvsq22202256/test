package com.easybet.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Event Kafka : Joueur créé
 * Infrastructure Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoueurCreeEvent {
    private Long joueurId;
    private String pseudo;
    private String email;
    private LocalDateTime timestamp;
}

