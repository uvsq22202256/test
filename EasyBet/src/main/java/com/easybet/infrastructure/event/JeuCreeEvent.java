package com.easybet.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class JeuCreeEvent {
    private String id;
    private String nom;
    private String type;
    private BigDecimal rtp;
    private LocalDateTime timestamp;
}