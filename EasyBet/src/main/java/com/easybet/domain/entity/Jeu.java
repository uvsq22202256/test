package com.easybet.domain.entity;

import java.math.BigDecimal;

public class Jeu {
    private String id;
    private String nom;
    private String type;
    private BigDecimal tauxRistourne; // RTP
    private boolean actif;

    public Jeu(String id, String nom, String type, BigDecimal tauxRistourne, boolean actif) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.tauxRistourne = tauxRistourne;
        this.actif = actif;
    }

    public void mettreAJour(String nom, String type, BigDecimal tauxRistourne) {
        if (nom != null && !nom.isBlank()) {
            this.nom = nom;
        }
        if (type != null && !type.isBlank()) {
            this.type = type;
        }
        if (tauxRistourne != null) {
            this.tauxRistourne = tauxRistourne;
        }
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public String getType() { return type; }
    public BigDecimal getTauxRistourne() { return tauxRistourne; }
    public boolean isActif() { return actif; }
}