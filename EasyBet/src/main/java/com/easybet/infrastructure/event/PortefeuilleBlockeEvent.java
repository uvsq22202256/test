package com.easybet.infrastructure.event;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Événement Kafka - Portefeuille bloqué
 */
public class PortefeuilleBlockeEvent implements Serializable {
    private Long portefeuilleId;
    private Long joueurId;
    private String pseudo;
    private String raison;
    private LocalDateTime dateBlockage;
    private LocalDateTime dateDeblocage;

    public PortefeuilleBlockeEvent() {}

    public PortefeuilleBlockeEvent(Long portefeuilleId, Long joueurId, String pseudo,
                                  String raison, LocalDateTime dateBlockage, LocalDateTime dateDeblocage) {
        this.portefeuilleId = portefeuilleId;
        this.joueurId = joueurId;
        this.pseudo = pseudo;
        this.raison = raison;
        this.dateBlockage = dateBlockage;
        this.dateDeblocage = dateDeblocage;
    }

    // Getters et Setters
    public Long getPortefeuilleId() { return portefeuilleId; }
    public void setPortefeuilleId(Long portefeuilleId) { this.portefeuilleId = portefeuilleId; }

    public Long getJoueurId() { return joueurId; }
    public void setJoueurId(Long joueurId) { this.joueurId = joueurId; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }

    public LocalDateTime getDateBlockage() { return dateBlockage; }
    public void setDateBlockage(LocalDateTime dateBlockage) { this.dateBlockage = dateBlockage; }

    public LocalDateTime getDateDeblocage() { return dateDeblocage; }
    public void setDateDeblocage(LocalDateTime dateDeblocage) { this.dateDeblocage = dateDeblocage; }
}

