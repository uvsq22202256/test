package com.easybet.adapters.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO Request pour créer une transaction
 * Utilisé pour POST /joueurs/{joueurId}/transactions
 *
 * Supporte tous les types : DEPOT, RETRAIT, BONUS, TRANSFERT
 */
public class TransactionRequestDTO {

    @NotBlank(message = "Le type de transaction est requis (DEPOT, RETRAIT, BONUS, TRANSFERT)")
    private String type; // DEPOT, RETRAIT, BONUS, TRANSFERT

    @NotNull(message = "Le montant est requis")
    @DecimalMin(value = "0.01", message = "Le montant doit être supérieur à 0")
    private BigDecimal montant;

    private String methodePayement; // Pour DEPOT/RETRAIT : CARTE_BANCAIRE, VIREMENT_BANCAIRE

    // Pour TRANSFERT
    private Long joueurDestinataireId;

    // Pour BONUS
    private String typeBonus; // BIENVENUE, FIDELITE, PROMOTION
    private String codePromo;

    // Constructeur
    public TransactionRequestDTO() {}

    public TransactionRequestDTO(String type, BigDecimal montant) {
        this.type = type;
        this.montant = montant;
    }

    // Getters et Setters
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public String getMethodePayement() { return methodePayement; }
    public void setMethodePayement(String methodePayement) { this.methodePayement = methodePayement; }

    public Long getJoueurDestinataireId() { return joueurDestinataireId; }
    public void setJoueurDestinataireId(Long joueurDestinataireId) { this.joueurDestinataireId = joueurDestinataireId; }

    public String getTypeBonus() { return typeBonus; }
    public void setTypeBonus(String typeBonus) { this.typeBonus = typeBonus; }

    public String getCodePromo() { return codePromo; }
    public void setCodePromo(String codePromo) { this.codePromo = codePromo; }
}

