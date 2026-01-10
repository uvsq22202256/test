package com.easybet.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entité JPA Portefeuille
 * Infrastructure Layer
 */
@Entity
@Table(name = "portefeuilles")
public class PortefeuilleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "joueur_id", nullable = false, unique = true)
    private Long joueurId;

    @Column(name = "pseudo", nullable = false)
    private String pseudo;

    @Column(name = "solde_reel", nullable = false)
    private BigDecimal soldeReel;

    @Column(name = "solde_bonus", nullable = false)
    private BigDecimal soldeBonus;

    @Column(name = "devise", nullable = false)
    private String devise;

    @Column(name = "statut", nullable = false)
    private String statut;

    @Column(name = "date_creation", nullable = false)
    private LocalDateTime dateCreation;

    @Column(name = "date_modification", nullable = false)
    private LocalDateTime dateModification;

    // Constructeurs
    public PortefeuilleEntity() {}

    public PortefeuilleEntity(Long joueurId, String pseudo, BigDecimal soldeReel, BigDecimal soldeBonus) {
        this.joueurId = joueurId;
        this.pseudo = pseudo;
        this.soldeReel = soldeReel;
        this.soldeBonus = soldeBonus;
        this.devise = "EUR";
        this.statut = "ACTIF";
        this.dateCreation = LocalDateTime.now();
        this.dateModification = LocalDateTime.now();
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

    public String getDevise() { return devise; }
    public void setDevise(String devise) { this.devise = devise; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}

