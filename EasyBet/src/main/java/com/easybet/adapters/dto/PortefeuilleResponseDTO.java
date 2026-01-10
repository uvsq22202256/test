package com.easybet.adapters.dto;

import com.easybet.domain.entity.Portefeuille;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO Response pour Portefeuille
 */
public class PortefeuilleResponseDTO {
    private Long id;
    private Long joueurId;
    private String pseudo;
    private BigDecimal soldeReel;
    private BigDecimal soldeBonus;
    private BigDecimal soldeTotal;
    private String devise;
    private String statut;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    // Constructeur
    public PortefeuilleResponseDTO() {}

    public PortefeuilleResponseDTO(Long id, Long joueurId, String pseudo, BigDecimal soldeReel,
                                  BigDecimal soldeBonus, String devise, String statut,
                                  LocalDateTime dateCreation, LocalDateTime dateModification) {
        this.id = id;
        this.joueurId = joueurId;
        this.pseudo = pseudo;
        this.soldeReel = soldeReel;
        this.soldeBonus = soldeBonus;
        this.devise = devise;
        this.statut = statut;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    // Factory method
    public static PortefeuilleResponseDTO fromDomain(Portefeuille portefeuille) {
        return new PortefeuilleResponseDTO(
            portefeuille.getId(),
            portefeuille.getJoueurId(),
            portefeuille.getPseudo(),
            portefeuille.getSoldeReel(),
            portefeuille.getSoldeBonus(),
            portefeuille.getDevise(),
            portefeuille.getStatut(),
            portefeuille.getDateCreation(),
            portefeuille.getDateModification()
        );
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getJoueurId() { return joueurId; }
    public void setJoueurId(Long joueurId) { this.joueurId = joueurId; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public BigDecimal getSoldeReel() { return soldeReel; }
    public void setSoldeReel(BigDecimal soldeReel) { this.soldeReel = soldeReel; }

    public BigDecimal getSoldeBonus() { return soldeBonus; }
    public void setSoldeBonus(BigDecimal soldeBonus) { this.soldeBonus = soldeBonus; }

    public BigDecimal getSoldeTotal() {
        return soldeReel != null && soldeBonus != null ? soldeReel.add(soldeBonus) : BigDecimal.ZERO;
    }

    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}

