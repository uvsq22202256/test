package com.easybet.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class SessionTermineeEvent {
    private String sessionId;
    private String joueurId;
    private BigDecimal gain;
    private LocalDateTime timestamp;
}