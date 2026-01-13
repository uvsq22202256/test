package com.easybet.adapters.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requete de creation joueur")
public class JoueurRequestDTO {

    @Schema(description = "Pseudo du joueur", example = "player123", required = true)
    @NotBlank(message = "Pseudo obligatoire")
    private String pseudo;

    @Schema(description = "Email du joueur", example = "player@easybet.com", required = true)
    @NotBlank(message = "Email obligatoire")
    @Email(message = "Format email invalide")
    private String email;

    @Schema(description = "Mot de passe du joueur", example = "SecurePass123!", required = true)
    @NotBlank(message = "Mot de passe obligatoire")
    private String password;
}