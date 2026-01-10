package com.easybet.adapters.dto;

import com.easybet.domain.entity.Joueur;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Informations d un joueur")
public class JoueurResponseDTO {

    @Schema(description = "ID du joueur", example = "1")
    private Long id;

    @Schema(description = "Pseudo du joueur", example = "player123")
    private String pseudo;

    @Schema(description = "Email du joueur", example = "player@easybet.com")
    private String email;

    @Schema(description = "Solde reel", example = "100.50")
    private double soldeReel;

    @Schema(description = "Solde bonus", example = "25.00")
    private double soldeBonus;

    @Schema(description = "Statut KYC", example = "false")
    private boolean kycValide;

    public static JoueurResponseDTO fromDomain(Joueur joueur) {
        return JoueurResponseDTO.builder()
                .id(joueur.getId())
                .pseudo(joueur.getPseudo())
                .email(joueur.getEmail())
                .soldeReel(joueur.getSoldeReel())
                .soldeBonus(joueur.getSoldeBonus())
                .kycValide(joueur.isKycValide())
                .build();
    }
}