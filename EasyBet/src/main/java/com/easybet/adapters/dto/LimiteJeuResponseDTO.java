package com.easybet.adapters.dto;

import com.easybet.domain.entity.LimiteJeu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Réponse contenant les limites de jeu d'un joueur")
public class LimiteJeuResponseDTO {

    @Schema(description = "ID de la configuration de limite", example = "limit-uuid-123")
    private String id;

    @Schema(description = "ID du joueur", example = "1")
    private String joueurId;

    @Schema(description = "Limite de dépôt hebdomadaire", example = "100.00")
    private BigDecimal limiteDepotHebdomadaire;

    // Méthode de mapping statique (comme dans tes autres DTOs)
    public static LimiteJeuResponseDTO fromDomain(LimiteJeu limite) {
        return LimiteJeuResponseDTO.builder()
                .id(limite.getId())
                .joueurId(limite.getJoueurId())
                .limiteDepotHebdomadaire(limite.getLimiteDepotHebdomadaire())
                .build();
    }
}