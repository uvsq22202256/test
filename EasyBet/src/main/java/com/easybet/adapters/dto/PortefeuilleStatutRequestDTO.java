package com.easybet.adapters.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * DTO Request pour modifier l'état du portefeuille
 * Utilisé pour PATCH /joueurs/{joueurId}/portefeuille
 */
public class PortefeuilleStatutRequestDTO {

    @NotBlank(message = "Le statut est requis (ACTIF ou BLOQUE)")
    private String statut; // ACTIF ou BLOQUE

    private String raison; // Pour BLOQUE
    private LocalDateTime dateDeblocage; // Pour BLOQUE

    // Constructeur
    public PortefeuilleStatutRequestDTO() {}

    public PortefeuilleStatutRequestDTO(String statut, String raison, LocalDateTime dateDeblocage) {
        this.statut = statut;
        this.raison = raison;
        this.dateDeblocage = dateDeblocage;
    }

    // Getters et Setters
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }

    public LocalDateTime getDateDeblocage() { return dateDeblocage; }
    public void setDateDeblocage(LocalDateTime dateDeblocage) { this.dateDeblocage = dateDeblocage; }
}

