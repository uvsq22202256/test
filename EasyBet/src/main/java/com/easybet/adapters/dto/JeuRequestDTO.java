package com.easybet.adapters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Requête de création d'un jeu")
public class JeuRequestDTO {

    @Schema(description = "Nom du jeu", example = "Roulette Royale", required = true)
    private String nom;

    @Schema(description = "Type de jeu (SLOT, TABLE, LIVE)", example = "TABLE", required = true)
    private String type;

    @Schema(description = "Taux de retour au joueur (0-1)", example = "0.97", required = true)
    private BigDecimal rtp;

    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public BigDecimal getRtp() { return rtp; }
    public void setRtp(BigDecimal rtp) { this.rtp = rtp; }
}