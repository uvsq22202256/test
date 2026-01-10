package com.easybet.adapters.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO Request pour Dépôt
 */
public class DepotRequestDTO {

    @NotNull(message = "Le montant est requis")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;

    @NotBlank(message = "La méthode de paiement est requise")
    private String methodePayement; // CARTE_BANCAIRE, VIREMENT_BANCAIRE, etc.

    private String reference;
    private String description;

    // Constructeur
    public DepotRequestDTO() {}

    public DepotRequestDTO(BigDecimal montant, String methodePayement, String reference) {
        this.montant = montant;
        this.methodePayement = methodePayement;
        this.reference = reference;
    }

    // Getters et Setters
    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public String getMethodePayement() { return methodePayement; }
    public void setMethodePayement(String methodePayement) { this.methodePayement = methodePayement; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

