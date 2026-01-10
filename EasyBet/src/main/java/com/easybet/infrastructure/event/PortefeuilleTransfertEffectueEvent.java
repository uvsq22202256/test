package com.easybet.infrastructure.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Événement Kafka - Transfert effectué
 */
public class PortefeuilleTransfertEffectueEvent implements Serializable {
    private Long portefeuilleSourceId;
    private Long joueurSourceId;
    private String pseudoSource;
    private Long portefeuilleDestinataireId;
    private Long joueurDestinataireId;
    private String pseudoDestinataire;
    private BigDecimal montant;
    private BigDecimal nouveauSoldeSource;
    private BigDecimal nouveauSoldeDestinataire;
    private LocalDateTime dateTransaction;

    public PortefeuilleTransfertEffectueEvent() {}

    public PortefeuilleTransfertEffectueEvent(Long portefeuilleSourceId, Long joueurSourceId, String pseudoSource,
                                             Long portefeuilleDestinataireId, Long joueurDestinataireId,
                                             String pseudoDestinataire, BigDecimal montant,
                                             BigDecimal nouveauSoldeSource, BigDecimal nouveauSoldeDestinataire,
                                             LocalDateTime dateTransaction) {
        this.portefeuilleSourceId = portefeuilleSourceId;
        this.joueurSourceId = joueurSourceId;
        this.pseudoSource = pseudoSource;
        this.portefeuilleDestinataireId = portefeuilleDestinataireId;
        this.joueurDestinataireId = joueurDestinataireId;
        this.pseudoDestinataire = pseudoDestinataire;
        this.montant = montant;
        this.nouveauSoldeSource = nouveauSoldeSource;
        this.nouveauSoldeDestinataire = nouveauSoldeDestinataire;
        this.dateTransaction = dateTransaction;
    }

    // Getters et Setters
    public Long getPortefeuilleSourceId() { return portefeuilleSourceId; }
    public void setPortefeuilleSourceId(Long portefeuilleSourceId) { this.portefeuilleSourceId = portefeuilleSourceId; }

    public Long getJoueurSourceId() { return joueurSourceId; }
    public void setJoueurSourceId(Long joueurSourceId) { this.joueurSourceId = joueurSourceId; }

    public String getPseudoSource() { return pseudoSource; }
    public void setPseudoSource(String pseudoSource) { this.pseudoSource = pseudoSource; }

    public Long getPortefeuilleDestinataireId() { return portefeuilleDestinataireId; }
    public void setPortefeuilleDestinataireId(Long portefeuilleDestinataireId) { this.portefeuilleDestinataireId = portefeuilleDestinataireId; }

    public Long getJoueurDestinataireId() { return joueurDestinataireId; }
    public void setJoueurDestinataireId(Long joueurDestinataireId) { this.joueurDestinataireId = joueurDestinataireId; }

    public String getPseudoDestinataire() { return pseudoDestinataire; }
    public void setPseudoDestinataire(String pseudoDestinataire) { this.pseudoDestinataire = pseudoDestinataire; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public BigDecimal getNouveauSoldeSource() { return nouveauSoldeSource; }
    public void setNouveauSoldeSource(BigDecimal nouveauSoldeSource) { this.nouveauSoldeSource = nouveauSoldeSource; }

    public BigDecimal getNouveauSoldeDestinataire() { return nouveauSoldeDestinataire; }
    public void setNouveauSoldeDestinataire(BigDecimal nouveauSoldeDestinataire) { this.nouveauSoldeDestinataire = nouveauSoldeDestinataire; }

    public LocalDateTime getDateTransaction() { return dateTransaction; }
    public void setDateTransaction(LocalDateTime dateTransaction) { this.dateTransaction = dateTransaction; }
}

