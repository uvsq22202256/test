package com.easybet.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Event Kafka : Joueur supprimé
 * Infrastructure Layer
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoueurSupprimeEvent {
    private Long joueurId;
    private LocalDateTime timestamp;
}

