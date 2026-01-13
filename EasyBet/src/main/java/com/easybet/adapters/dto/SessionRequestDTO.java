package com.easybet.adapters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Requête de démarrage de session")
public class SessionRequestDTO {

    @Schema(description = "ID du joueur", example = "1", required = true)
    private String joueurId;

    @Schema(description = "ID du jeu", example = "uuid-jeu-123", required = true)
    private String jeuId;

    @Schema(description = "Montant de la mise", example = "10.00", required = true)
    private BigDecimal mise;

    // Getters et Setters
    public String getJoueurId() { return joueurId; }
    public void setJoueurId(String joueurId) { this.joueurId = joueurId; }
    public String getJeuId() { return jeuId; }
    public void setJeuId(String jeuId) { this.jeuId = jeuId; }
    public BigDecimal getMise() { return mise; }
    public void setMise(BigDecimal mise) { this.mise = mise; }
}