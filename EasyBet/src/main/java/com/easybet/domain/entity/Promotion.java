package com.easybet.domain.entity;

import java.time.LocalDate;

public class Promotion {
    private Long id;
    private String code;
    private Double montant;
    private LocalDate dateFin;

    public Promotion() {}

    public Promotion(String code, Double montant, LocalDate dateFin) {
        this.code = code;
        this.montant = montant;
        this.dateFin = dateFin;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
}