package com.easybet.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SessionJeu {
    private String id;
    private String joueurId;
    private String jeuId;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private BigDecimal mise;
    private BigDecimal gain;
    private String statut; // EN_COURS, TERMINEE

    public SessionJeu(String id, String joueurId, String jeuId, BigDecimal mise, LocalDateTime dateDebut, LocalDateTime dateFin, BigDecimal gain, String statut) {
        this.id = id;
        this.joueurId = joueurId;
        this.jeuId = jeuId;
        this.mise = mise;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.gain = gain;
        this.statut = statut;
    }

    // Factory method
    public static SessionJeu creer(String id, String joueurId, String jeuId, BigDecimal mise) {
        return new SessionJeu(id, joueurId, jeuId, mise, LocalDateTime.now(), null, BigDecimal.ZERO, "EN_COURS");
    }

    public void terminer(BigDecimal gain) {
        this.gain = gain;
        this.dateFin = LocalDateTime.now();
        this.statut = "TERMINEE";
    }

    public String getId() { return id; }
    public String getJoueurId() { return joueurId; }
    public String getJeuId() { return jeuId; }
    public LocalDateTime getDateDebut() { return dateDebut; }
    public LocalDateTime getDateFin() { return dateFin; }
    public BigDecimal getMise() { return mise; }
    public BigDecimal getGain() { return gain; }
    public String getStatut() { return statut; }
}