package com.easybet.domain.entity;

import java.math.BigDecimal;

public class LimiteJeu {
    private String id;
    private String joueurId;
    private BigDecimal limiteDepotHebdomadaire;

    public LimiteJeu(String id, String joueurId, BigDecimal limiteDepotHebdomadaire) {
        this.id = id;
        this.joueurId = joueurId;
        this.limiteDepotHebdomadaire = limiteDepotHebdomadaire;
    }

    // Méthode métier
    public void modifierLimite(BigDecimal nouvelleLimite) {
        if (nouvelleLimite == null || nouvelleLimite.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La limite doit être positive");
        }
        this.limiteDepotHebdomadaire = nouvelleLimite;
    }

    public String getId() { return id; }
    public String getJoueurId() { return joueurId; }
    public BigDecimal getLimiteDepotHebdomadaire() { return limiteDepotHebdomadaire; }
}