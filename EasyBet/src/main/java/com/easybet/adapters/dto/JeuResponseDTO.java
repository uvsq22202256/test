package com.easybet.adapters.dto;

import com.easybet.domain.entity.Jeu;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Informations sur un jeu")
public class JeuResponseDTO {

    @Schema(description = "ID unique du jeu", example = "uuid-1234")
    private String id;

    @Schema(description = "Nom du jeu", example = "Poker Texas Hold'em")
    private String nom;

    @Schema(description = "Type de jeu", example = "TABLE")
    private String type;

    @Schema(description = "Taux de retour au joueur (RTP)", example = "0.98")
    private BigDecimal tauxRistourne;

    @Schema(description = "Jeu actif ou non", example = "true")
    private boolean actif;

    public JeuResponseDTO(String id, String nom, String type, BigDecimal tauxRistourne, boolean actif) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.tauxRistourne = tauxRistourne;
        this.actif = actif;
    }

    public static JeuResponseDTO fromDomain(Jeu jeu) {
        return new JeuResponseDTO(jeu.getId(), jeu.getNom(), jeu.getType(), jeu.getTauxRistourne(), jeu.isActif());
    }

    // Getters
    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getType() { return type; }
    public BigDecimal getTauxRistourne() { return tauxRistourne; }
    public boolean isActif() { return actif; }
}