package com.easybet.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité domaine Transaction
 * Représente une transaction financière (dépôt, retrait, bonus, transfert)
 */
public class Transaction {
    private Long id;
    private Long portefeuilleId;
    private Long portefeuilleDestinataireId; // Pour les transferts
    private String typeTransaction; // DEPOT, RETRAIT, BONUS, TRANSFERT
    private BigDecimal montant;
    private BigDecimal ancienSolde;
    private BigDecimal nouveauSolde;
    private String statut; // COMPLETE, PENDING, FAILED
    private LocalDateTime dateTransaction;
    private String reference; // Référence unique
    private String methodePayement; // CARTE_BANCAIRE, VIREMENT_BANCAIRE, PORTEFEUILLE
    private String description;

    // Constructeurs
    public Transaction() {}

    public Transaction(Long portefeuilleId, String typeTransaction, BigDecimal montant,
                      BigDecimal ancienSolde, BigDecimal nouveauSolde) {
        this.portefeuilleId = portefeuilleId;
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.ancienSolde = ancienSolde;
        this.nouveauSolde = nouveauSolde;
        this.statut = "COMPLETE";
        this.dateTransaction = LocalDateTime.now();
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPortefeuilleId() { return portefeuilleId; }
    public void setPortefeuilleId(Long portefeuilleId) { this.portefeuilleId = portefeuilleId; }

    public Long getPortefeuilleDestinataireId() { return portefeuilleDestinataireId; }
    public void setPortefeuilleDestinataireId(Long portefeuilleDestinataireId) {
        this.portefeuilleDestinataireId = portefeuilleDestinataireId;
    }

    public String getTypeTransaction() { return typeTransaction; }
    public void setTypeTransaction(String typeTransaction) { this.typeTransaction = typeTransaction; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public BigDecimal getAncienSolde() { return ancienSolde; }
    public void setAncienSolde(BigDecimal ancienSolde) { this.ancienSolde = ancienSolde; }

    public BigDecimal getNouveauSolde() { return nouveauSolde; }
    public void setNouveauSolde(BigDecimal nouveauSolde) { this.nouveauSolde = nouveauSolde; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateTransaction() { return dateTransaction; }
    public void setDateTransaction(LocalDateTime dateTransaction) { this.dateTransaction = dateTransaction; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getMethodePayement() { return methodePayement; }
    public void setMethodePayement(String methodePayement) { this.methodePayement = methodePayement; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

