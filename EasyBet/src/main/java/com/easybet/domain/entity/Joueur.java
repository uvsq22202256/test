package com.easybet.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité métier Joueur - Domain Layer
 * SANS annotations Spring/JPA (Clean Architecture)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Joueur {
    private Long id;
    private String pseudo;
    private String email;
    private String password;
    private double soldeReel;
    private double soldeBonus;
    private boolean kycValide;

    /**
     * Méthode métier : Dépôt d'argent
     */
    public void deposer(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }
        this.soldeReel += montant;
    }

    /**
     * Méthode métier : Retrait d'argent
     */
    public void retirer(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Montant invalide");
        }
        if (this.soldeReel < montant) {
            throw new IllegalStateException("Solde insuffisant");
        }
        this.soldeReel -= montant;
    }

    /**
     * Méthode métier : Validation KYC
     */
    public void validerKyc() {
        this.kycValide = true;
    }
}

