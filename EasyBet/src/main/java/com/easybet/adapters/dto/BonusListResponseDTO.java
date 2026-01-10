package com.easybet.adapters.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO Response pour lister les bonus d'un joueur
 * Utilisé pour GET /joueurs/{joueurId}/bonus
 */
public class BonusListResponseDTO {
    private BigDecimal soldeBonus;
    private List<TransactionResponseDTO> bonus;

    // Constructeur
    public BonusListResponseDTO() {}

    public BonusListResponseDTO(BigDecimal soldeBonus, List<TransactionResponseDTO> bonus) {
        this.soldeBonus = soldeBonus;
        this.bonus = bonus;
    }

    // Getters et Setters
    public BigDecimal getSoldeBonus() { return soldeBonus; }
    public void setSoldeBonus(BigDecimal soldeBonus) { this.soldeBonus = soldeBonus; }

    public List<TransactionResponseDTO> getBonus() { return bonus; }
    public void setBonus(List<TransactionResponseDTO> bonus) { this.bonus = bonus; }
}














