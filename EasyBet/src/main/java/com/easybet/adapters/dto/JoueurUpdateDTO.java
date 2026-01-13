package com.easybet.adapters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour la mise à jour d'un joueur
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requete de modification joueur")
public class JoueurUpdateDTO {

    @Schema(description = "Nouveau pseudo (optionnel)", example = "newplayer123")
    private String pseudo;

    @Schema(description = "Nouvel email (optionnel)", example = "newplayer@easybet.com")
    private String email;

    @Schema(description = "Nouveau mot de passe (optionnel)", example = "NewPass456!")
    private String password;

    @Schema(description = "Nouveau statut KYC (optionnel)", example = "true")
    private Boolean kycValide;
}

