package com.easybet.adapters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requête de définition d'une limite de jeu")
public class LimiteJeuRequestDTO {

    @Schema(description = "ID du joueur", example = "1", required = true)
    @NotBlank(message = "L'ID du joueur est obligatoire")
    private String joueurId;

    @Schema(description = "Montant de la limite hebdomadaire", example = "100.00", required = true)
    @NotNull(message = "Le montant est requis")
    @DecimalMin(value = "0.01", message = "La limite doit être supérieure à 0")
    private BigDecimal montantLimite;
}