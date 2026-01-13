package com.easybet.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class JeuModifieEvent {
    private String id;
    private String ancienNom;
    private String nouveauNom;
    private BigDecimal nouveauRtp;
    private LocalDateTime timestamp;
}