package com.easybet.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité domaine Portefeuille
 * Représente le portefeuille financier d'un joueur
 */
public class Portefeuille {
    private Long id;
    private Long joueurId;
    private String pseudo;
    private BigDecimal soldeReel;
    private BigDecimal soldeBonus;
    private String devise;
    private String statut; // ACTIF, BLOQUE, SUSPENDU
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    // Constructeurs
    public Portefeuille() {}

    public Portefeuille(Long joueurId, String pseudo, BigDecimal soldeReel, BigDecimal soldeBonus) {
        this.joueurId = joueurId;
        this.pseudo = pseudo;
        this.soldeReel = soldeReel;
        this.soldeBonus = soldeBonus;
        this.devise = "EUR";
        this.statut = "ACTIF";
        this.dateCreation = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJoueurId() { return joueurId; }
    public void setJoueurId(Long joueurId) { this.joueurId = joueurId; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public BigDecimal getSoldeReel() { return soldeReel; }
    public void setSoldeReel(BigDecimal soldeReel) { this.soldeReel = soldeReel; }

    public BigDecimal getSoldeBonus() { return soldeBonus; }
    public void setSoldeBonus(BigDecimal soldeBonus) { this.soldeBonus = soldeBonus; }

    public BigDecimal getSoldeTotal() {
        return soldeReel.add(soldeBonus);
    }

    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}

