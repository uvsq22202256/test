package com.easybet.infrastructure.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Événement Kafka - Bonus ajouté
 */
public class PortefeuilleBonusAjouteEvent implements Serializable {
    private Long portefeuilleId;
    private Long joueurId;
    private String pseudo;
    private BigDecimal montant;
    private BigDecimal nouveauSoldeBonus;
    private String typeBonus;
    private String codePromo;
    private LocalDateTime dateTransaction;

    public PortefeuilleBonusAjouteEvent() {}

    public PortefeuilleBonusAjouteEvent(Long portefeuilleId, Long joueurId, String pseudo,
                                       BigDecimal montant, BigDecimal nouveauSoldeBonus,
                                       String typeBonus, String codePromo, LocalDateTime dateTransaction) {
        this.portefeuilleId = portefeuilleId;
        this.joueurId = joueurId;
        this.pseudo = pseudo;
        this.montant = montant;
        this.nouveauSoldeBonus = nouveauSoldeBonus;
        this.typeBonus = typeBonus;
        this.codePromo = codePromo;
        this.dateTransaction = dateTransaction;
    }

    // Getters et Setters
    public Long getPortefeuilleId() { return portefeuilleId; }
    public void setPortefeuilleId(Long portefeuilleId) { this.portefeuilleId = portefeuilleId; }

    public Long getJoueurId() { return joueurId; }
    public void setJoueurId(Long joueurId) { this.joueurId = joueurId; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public BigDecimal getNouveauSoldeBonus() { return nouveauSoldeBonus; }
    public void setNouveauSoldeBonus(BigDecimal nouveauSoldeBonus) { this.nouveauSoldeBonus = nouveauSoldeBonus; }

    public String getTypeBonus() { return typeBonus; }
    public void setTypeBonus(String typeBonus) { this.typeBonus = typeBonus; }

    public String getCodePromo() { return codePromo; }
    public void setCodePromo(String codePromo) { this.codePromo = codePromo; }

    public LocalDateTime getDateTransaction() { return dateTransaction; }
    public void setDateTransaction(LocalDateTime dateTransaction) { this.dateTransaction = dateTransaction; }
}

