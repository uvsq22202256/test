package com.easybet.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SessionDemarreeEvent {
    private String sessionId;
    private String joueurId;
    private String jeuId;
    private BigDecimal mise;
    private LocalDateTime timestamp;
}