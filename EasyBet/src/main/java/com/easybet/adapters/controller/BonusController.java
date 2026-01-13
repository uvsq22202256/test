package com.easybet.adapters.controller;

import com.easybet.usecase.AjouterBonusUseCase;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Donner un bonus manuel", description = "L'admin donne de l'argent via l'ID joueur.")
    public ResponseEntity<String> donnerBonusManuel(
            @RequestParam Long joueurId,
            @RequestParam BigDecimal montant,
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
    @Operation(summary = "Appliquer offre bienvenue (Dépôt)", description = "Bonus si dépôt >= 20€.")
    public ResponseEntity<String> offreBienvenue(
            @RequestParam Long joueurId,
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
    @Operation(summary = "Utiliser un code promo", description = "L'ID de la promo est automatique, on ne demande que le JOUEUR ID et le CODE.")
    public ResponseEntity<String> appliquerCodePromo(
            @RequestParam Long joueurId,
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