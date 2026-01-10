package com.easybet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité JPA Transaction
 * Infrastructure Layer
 */
@Entity
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "portefeuille_id", nullable = false)
    private Long portefeuilleId;

    @Column(name = "portefeuille_destinataire_id")
    private Long portefeuilleDestinataireId;

    @Column(name = "type_transaction", nullable = false)
    private String typeTransaction;

    @Column(name = "montant", nullable = false)
    private BigDecimal montant;

    @Column(name = "ancien_solde", nullable = false)
    private BigDecimal ancienSolde;

    @Column(name = "nouveau_solde", nullable = false)
    private BigDecimal nouveauSolde;

    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "date_transaction", nullable = false)
    private LocalDateTime dateTransaction;

    @Column(name = "reference", unique = true)
    private String reference;

    @Column(name = "methode_payement")
    private String methodePayement;

    @Column(name = "description")
    private String description;

    // Constructeurs
    public TransactionEntity() {}

    public TransactionEntity(Long portefeuilleId, String typeTransaction, BigDecimal montant,
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
    public void setPortefeuilleDestinataireId(Long portefeuilleDestinataireId) { this.portefeuilleDestinataireId = portefeuilleDestinataireId; }

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

