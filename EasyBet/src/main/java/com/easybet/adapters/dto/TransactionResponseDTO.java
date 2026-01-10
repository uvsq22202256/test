package com.easybet.adapters.dto;

import com.easybet.domain.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO Response pour Transaction
 */
public class TransactionResponseDTO {
    private Long id;
    private Long portefeuilleId;
    private Long portefeuilleDestinataireId;
    private String typeTransaction;
    private BigDecimal montant;
    private BigDecimal ancienSolde;
    private BigDecimal nouveauSolde;
    private String statut;
    private LocalDateTime dateTransaction;
    private String reference;
    private String methodePayement;
    private String description;

    // Constructeur
    public TransactionResponseDTO() {}

    public TransactionResponseDTO(Long id, Long portefeuilleId, Long portefeuilleDestinataireId,
                                 String typeTransaction, BigDecimal montant, BigDecimal ancienSolde,
                                 BigDecimal nouveauSolde, String statut, LocalDateTime dateTransaction,
                                 String reference, String methodePayement, String description) {
        this.id = id;
        this.portefeuilleId = portefeuilleId;
        this.portefeuilleDestinataireId = portefeuilleDestinataireId;
        this.typeTransaction = typeTransaction;
        this.montant = montant;
        this.ancienSolde = ancienSolde;
        this.nouveauSolde = nouveauSolde;
        this.statut = statut;
        this.dateTransaction = dateTransaction;
        this.reference = reference;
        this.methodePayement = methodePayement;
        this.description = description;
    }

    // Factory method
    public static TransactionResponseDTO fromDomain(Transaction transaction) {
        return new TransactionResponseDTO(
            transaction.getId(),
            transaction.getPortefeuilleId(),
            transaction.getPortefeuilleDestinataireId(),
            transaction.getTypeTransaction(),
            transaction.getMontant(),
            transaction.getAncienSolde(),
            transaction.getNouveauSolde(),
            transaction.getStatut(),
            transaction.getDateTransaction(),
            transaction.getReference(),
            transaction.getMethodePayement(),
            transaction.getDescription()
        );
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

