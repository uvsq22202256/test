package com.easybet.adapters.controller;

import com.easybet.usecase.AjouterBonusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/bonus")
@Tag(name = "Bonus (Admin)", description = "Accorder des bonus manuels ou sur condition aux joueurs")
public class BonusController {

    private final AjouterBonusUseCase ajouterBonusUseCase;

    public BonusController(AjouterBonusUseCase ajouterBonusUseCase) {
        this.ajouterBonusUseCase = ajouterBonusUseCase;
    }

    // --- 1. TON CODE DE BASE : DON MANUEL ---
    @PostMapping("/donner")
    @Operation(summary = "Donner un bonus manuel", description = "L'administrateur accorde un bonus manuel à un joueur avec un montant et une raison spécifiques")
    @ApiResponse(responseCode = "200", description = "Bonus accordé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides ou joueur non trouvé")
    public ResponseEntity<String> donnerBonusManuel(
            @Parameter(description = "ID du joueur", required = true)
            @RequestParam Long joueurId,
            @Parameter(description = "Montant du bonus en euros", required = true)
            @RequestParam BigDecimal montant,
            @Parameter(description = "Raison du bonus", required = true)
            @RequestParam String raison
    ) {
        try {
            ajouterBonusUseCase.execute(joueurId, montant, "BONUS_MANUEL", raison);
            return ResponseEntity.ok("Succès : Bonus de " + montant + "€ envoyé au joueur " + joueurId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    // --- 2. TON CODE DE BASE : OFFRE DÉPÔT ---
    @PostMapping("/offre-bienvenue")
    @Operation(summary = "Appliquer offre bienvenue (Dépôt)", description = "Accorde un bonus de bienvenue de 10€ si le dépôt est supérieur ou égal à 20€")
    @ApiResponse(responseCode = "200", description = "Bonus de bienvenue accordé avec succès")
    @ApiResponse(responseCode = "400", description = "Dépôt insuffisant (minimum 20€)")
    public ResponseEntity<String> offreBienvenue(
            @Parameter(description = "ID du joueur", required = true)
            @RequestParam Long joueurId,
            @Parameter(description = "Montant du dépôt en euros", required = true)
            @RequestParam BigDecimal montantDepot
    ) {
        BigDecimal seuilMinimal = new BigDecimal("20");
        BigDecimal montantCadeau = new BigDecimal("10");

        if (montantDepot.compareTo(seuilMinimal) >= 0) {
            try {
                ajouterBonusUseCase.execute(joueurId, montantCadeau, "OFFRE_DEPOT", "Bonus dépôt de " + montantDepot + "€");
                return ResponseEntity.ok("Félicitations ! Bonus de 10€ ajouté pour votre dépôt de " + montantDepot + "€.");
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
            }
        } else {
            return ResponseEntity.badRequest().body("Dépôt insuffisant pour l'offre. Minimum requis : 20€.");
        }
    }

    // --- 3. TA PARTIE PROMOTION CORRIGÉE ---
    @PostMapping("/appliquer-code")
    @Operation(summary = "Utiliser un code promo", description = "Applique un code promotionnel valide (ex: NOEL2026, FINAL2026) pour accorder 15€ de bonus au joueur")
    @ApiResponse(responseCode = "200", description = "Code promo appliqué avec succès - 15€ ajoutés")
    @ApiResponse(responseCode = "400", description = "Code promo invalide ou joueur non trouvé")
    public ResponseEntity<String> appliquerCodePromo(
            @Parameter(description = "ID du joueur", required = true)
            @RequestParam Long joueurId,
            @Parameter(description = "Code promotionnel (ex: NOEL2026, FINAL2026)", required = true)
            @RequestParam String codePromo
    ) {
        // CORRECTION : On accepte maintenant ton code "FINAL2026" en plus de "NOEL2026"
        if ("NOEL2026".equalsIgnoreCase(codePromo) || "FINAL2026".equalsIgnoreCase(codePromo)) {
            try {
                ajouterBonusUseCase.execute(joueurId, new BigDecimal("15"), "PROMO_CODE", "Code promo utilisé : " + codePromo);
                // Retourne Code 200 (Vert)
                return ResponseEntity.ok("Succès : Le code " + codePromo + " a ajouté 15€ au joueur " + joueurId);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
            }
        } else {
            // Retourne Code 400 (Rouge)
            return ResponseEntity.badRequest().body("Erreur : Le code promo '" + codePromo + "' est invalide.");
        }
    }
}