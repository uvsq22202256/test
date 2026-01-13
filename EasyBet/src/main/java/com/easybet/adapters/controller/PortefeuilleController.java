package com.easybet.adapters.controller;

import com.easybet.adapters.dto.*;
import com.easybet.domain.entity.Portefeuille;
import com.easybet.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/portefeuilles")
@Tag(name = "Portefeuilles", description = "API RESTful de gestion des portefeuilles avec séparation des soldes")
public class PortefeuilleController {

    private final GetPortefeuilleUseCase getPortefeuilleUseCase;
    private final BloquerPortefeuilleUseCase bloquerPortefeuilleUseCase;
    private final DebloquerPortefeuilleUseCase debloquerPortefeuilleUseCase;
    private final SupprimerPortefeuilleUseCase supprimerPortefeuilleUseCase;
    private final AjouterBonusUseCase ajouterBonusUseCase;

    public PortefeuilleController(GetPortefeuilleUseCase getPortefeuilleUseCase,
                                  BloquerPortefeuilleUseCase bloquerPortefeuilleUseCase,
                                  DebloquerPortefeuilleUseCase debloquerPortefeuilleUseCase,
                                  SupprimerPortefeuilleUseCase supprimerPortefeuilleUseCase,
                                  AjouterBonusUseCase ajouterBonusUseCase) {
        this.getPortefeuilleUseCase = getPortefeuilleUseCase;
        this.bloquerPortefeuilleUseCase = bloquerPortefeuilleUseCase;
        this.debloquerPortefeuilleUseCase = debloquerPortefeuilleUseCase;
        this.supprimerPortefeuilleUseCase = supprimerPortefeuilleUseCase;
        this.ajouterBonusUseCase = ajouterBonusUseCase;
    }

    /**
     * POST /portefeuilles/deposer
     * Sépare le dépôt (Solde Réel) et le bonus automatique (Solde Bonus)
     */
    @PostMapping("/deposer")
    @Operation(summary = "Déposer de l'argent + Bonus",
            description = "Crédite le solde RÉEL. Offre 10€ de solde BONUS si le dépôt est de 20€ ou plus.")
    @ApiResponse(responseCode = "200", description = "Dépôt (Réel) et Bonus (Cadeau) traités")
    public ResponseEntity<String> deposer(
            @RequestParam Long joueurId,
            @RequestParam BigDecimal montant) {

        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Le montant doit être supérieur à 0€.");
        }

        try {
            // 1. Créditer le dépôt dans le SOLDE RÉEL
            // On utilise le type "DEPOT" qui cible généralement le solde réel dans ton système
            ajouterBonusUseCase.execute(joueurId, montant, "DEPOT", "Dépôt d'argent réel par l'utilisateur");

            StringBuilder messageSuccs = new StringBuilder("Succès : Votre dépôt réel de " + montant + "€ a été crédité.");

            // 2. LOGIQUE DE BONUS (SOLDE BONUS)
            BigDecimal seuilMinimal = new BigDecimal("20");
            BigDecimal montantBonus = new BigDecimal("10");

            if (montant.compareTo(seuilMinimal) >= 0) {
                // On utilise le type "PROMO_CODE" ou "BONUS" qui cible le solde bonus
                ajouterBonusUseCase.execute(joueurId, montantBonus, "PROMO_CODE", "Bonus automatique (Dépôt >= 20€)");
                messageSuccs.append(" Félicitations ! Un bonus de 10€ a été ajouté à votre solde bonus.");
            }

            return ResponseEntity.ok(messageSuccs.toString());
        } catch (RuntimeException e) {
            // Gestion de l'erreur si le portefeuille est introuvable
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping
    @Operation(summary = "Récupérer un portefeuille")
    public ResponseEntity<PortefeuilleResponseDTO> getPortefeuille(@RequestParam Long joueurId) {
        Portefeuille portefeuille = getPortefeuilleUseCase.execute(joueurId);
        return ResponseEntity.ok(PortefeuilleResponseDTO.fromDomain(portefeuille));
    }

    @GetMapping("/{portefeuilleId}")
    @Operation(summary = "Récupérer les détails d'un portefeuille")
    public ResponseEntity<PortefeuilleResponseDTO> getPortefeuilleById(@PathVariable Long portefeuilleId) {
        Portefeuille portefeuille = getPortefeuilleUseCase.execute(portefeuilleId);
        return ResponseEntity.ok(PortefeuilleResponseDTO.fromDomain(portefeuille));
    }

    @PatchMapping("/{portefeuilleId}")
    @Operation(summary = "Modifier l'état du portefeuille")
    public ResponseEntity<PortefeuilleResponseDTO> modifierEtatPortefeuille(
            @PathVariable Long portefeuilleId,
            @Valid @RequestBody PortefeuilleStatutRequestDTO request) {

        Portefeuille portefeuille;
        if ("BLOQUE".equals(request.getStatut())) {
            portefeuille = bloquerPortefeuilleUseCase.execute(portefeuilleId, request.getRaison(), request.getDateDeblocage());
        } else if ("ACTIF".equals(request.getStatut())) {
            portefeuille = debloquerPortefeuilleUseCase.execute(portefeuilleId);
        } else {
            throw new RuntimeException("Statut invalide.");
        }
        return ResponseEntity.ok(PortefeuilleResponseDTO.fromDomain(portefeuille));
    }

    @DeleteMapping("/{portefeuilleId}")
    @Operation(summary = "Supprimer un portefeuille")
    public ResponseEntity<Void> supprimerPortefeuille(@PathVariable Long portefeuilleId) {
        supprimerPortefeuilleUseCase.execute(portefeuilleId);
        return ResponseEntity.noContent().build();
    }
}