package com.easybet.adapters.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO Request pour Transfert de fonds
 */
public class TransfertRequestDTO {

    @NotNull(message = "Le montant à transférer est requis")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montantTransfert;

    @NotNull(message = "L'ID du joueur destinataire est requis")
    private Long joueurDestinataireId;

    private String typeTransfert; // ENTRE_JOUEURS
    private String description;

    // Constructeur
    public TransfertRequestDTO() {}

    public TransfertRequestDTO(BigDecimal montantTransfert, Long joueurDestinataireId) {
        this.montantTransfert = montantTransfert;
        this.joueurDestinataireId = joueurDestinataireId;
        this.typeTransfert = "ENTRE_JOUEURS";
    }

    // Getters et Setters
    public BigDecimal getMontantTransfert() { return montantTransfert; }
    public void setMontantTransfert(BigDecimal montantTransfert) { this.montantTransfert = montantTransfert; }

    public Long getJoueurDestinataireId() { return joueurDestinataireId; }
    public void setJoueurDestinataireId(Long joueurDestinataireId) { this.joueurDestinataireId = joueurDestinataireId; }

    public String getTypeTransfert() { return typeTransfert; }
    public void setTypeTransfert(String typeTransfert) { this.typeTransfert = typeTransfert; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

