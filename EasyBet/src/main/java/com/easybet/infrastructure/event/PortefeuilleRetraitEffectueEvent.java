package com.easybet.infrastructure.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Événement Kafka - Retrait effectué
 */
public class PortefeuilleRetraitEffectueEvent implements Serializable {
    private Long portefeuilleId;
    private Long joueurId;
    private String pseudo;
    private BigDecimal montant;
    private BigDecimal nouveauSoldeReel;
    private LocalDateTime dateTransaction;
    private String reference;

    public PortefeuilleRetraitEffectueEvent() {}

    public PortefeuilleRetraitEffectueEvent(Long portefeuilleId, Long joueurId, String pseudo,
                                           BigDecimal montant, BigDecimal nouveauSoldeReel,
                                           LocalDateTime dateTransaction, String reference) {
        this.portefeuilleId = portefeuilleId;
        this.joueurId = joueurId;
        this.pseudo = pseudo;
        this.montant = montant;
        this.nouveauSoldeReel = nouveauSoldeReel;
        this.dateTransaction = dateTransaction;
        this.reference = reference;
    }

    // Getters et Setters
    public Long getPortefeuilleId() { return portefeuilleId; }
    public void setPortefeuilleId(Long portefeuilleId) { this.portefeuilleId = portefeuilleId; }

    public Long getJoueurId() { return joueurId; }
    public void setJoueurId(Long joueurId) { this.joueurId = joueurId; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public BigDecimal getNouveauSoldeReel() { return nouveauSoldeReel; }
    public void setNouveauSoldeReel(BigDecimal nouveauSoldeReel) { this.nouveauSoldeReel = nouveauSoldeReel; }

    public LocalDateTime getDateTransaction() { return dateTransaction; }
    public void setDateTransaction(LocalDateTime dateTransaction) { this.dateTransaction = dateTransaction; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}

