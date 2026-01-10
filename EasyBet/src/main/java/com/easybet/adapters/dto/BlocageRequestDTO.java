package com.easybet.adapters.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * DTO Request pour Blocage/Déblocage de portefeuille
 */
public class BlocageRequestDTO {

    @NotBlank(message = "La raison du blocage est requise")
    private String raison;

    private LocalDateTime dateDeblocage;

    // Constructeur
    public BlocageRequestDTO() {}

    public BlocageRequestDTO(String raison, LocalDateTime dateDeblocage) {
        this.raison = raison;
        this.dateDeblocage = dateDeblocage;
    }

    // Getters et Setters
    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }

    public LocalDateTime getDateDeblocage() { return dateDeblocage; }
    public void setDateDeblocage(LocalDateTime dateDeblocage) { this.dateDeblocage = dateDeblocage; }
}

