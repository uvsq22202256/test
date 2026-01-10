package com.easybet.adapters.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO Request pour Bonus
 */
public class BonusRequestDTO {

    @NotNull(message = "Le montant du bonus est requis")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;

    @NotBlank(message = "Le type de bonus est requis")
    private String typeBonus; // BIENVENUE, FIDELITE, PROMOTION, etc.

    private String codePromo;
    private String description;

    // Constructeur
    public BonusRequestDTO() {}

    public BonusRequestDTO(BigDecimal montant, String typeBonus, String codePromo) {
        this.montant = montant;
        this.typeBonus = typeBonus;
        this.codePromo = codePromo;
    }

    // Getters et Setters
    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public String getTypeBonus() { return typeBonus; }
    public void setTypeBonus(String typeBonus) { this.typeBonus = typeBonus; }

    public String getCodePromo() { return codePromo; }
    public void setCodePromo(String codePromo) { this.codePromo = codePromo; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

