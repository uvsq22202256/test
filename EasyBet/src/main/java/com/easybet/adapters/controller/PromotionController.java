package com.easybet.adapters.controller;

import com.easybet.domain.entity.Promotion;
import com.easybet.usecase.AppliquerPromotionUseCase;
import com.easybet.usecase.CreerPromotionUsecase;
import com.easybet.usecase.RecupererPromotionActivesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/promotions")
@Tag(name = "Promotions", description = "API de gestion des promotions et codes promo")
public class PromotionController {

    private final CreerPromotionUsecase creerPromotionUsecase;
    private final RecupererPromotionActivesUseCase recupererActivesUseCase;
    private final AppliquerPromotionUseCase appliquerPromotionUseCase;

    public PromotionController(CreerPromotionUsecase creerPromotionUsecase,
                               RecupererPromotionActivesUseCase recupererActivesUseCase,
                               AppliquerPromotionUseCase appliquerPromotionUseCase) {
        this.creerPromotionUsecase = creerPromotionUsecase;
        this.recupererActivesUseCase = recupererActivesUseCase;
        this.appliquerPromotionUseCase = appliquerPromotionUseCase;
    }

    @PostMapping
    @Operation(summary = "Créer une promotion (Admin)",
               description = "Crée une nouvelle promotion avec un code, un montant et une date de fin de validité")
    @ApiResponse(responseCode = "201", description = "Promotion créée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<Promotion> creerPromotion(@RequestBody PromotionRequest request) {
        Promotion nouvellePromo = new Promotion();
        nouvellePromo.setCode(request.code);

        // FIX 1 : Correction du type pour le montant
        nouvellePromo.setMontant(request.montant.doubleValue());

        // FIX 2 : Correction du type pour la date (on prend juste la partie Date)
        nouvellePromo.setDateFin(request.dateFin.toLocalDate());

        return ResponseEntity.status(201).body(creerPromotionUsecase.execute(nouvellePromo));
    }

    @GetMapping("/actives")
    @Operation(summary = "Lister les promotions actives",
               description = "Retourne la liste de toutes les promotions actuellement valides et non expirées")
    @ApiResponse(responseCode = "200", description = "Liste des promotions actives récupérée avec succès")
    public ResponseEntity<List<Promotion>> listerPromotionsActives() {
        return ResponseEntity.ok(recupererActivesUseCase.execute());
    }

    @PostMapping("/appliquer")
    @Operation(summary = "Appliquer une promotion",
               description = "Applique une promotion à un joueur en utilisant son code promo")
    @ApiResponse(responseCode = "200", description = "Promotion appliquée avec succès")
    @ApiResponse(responseCode = "400", description = "Code promo invalide ou expiré")
    @ApiResponse(responseCode = "404", description = "Joueur non trouvé")
    public ResponseEntity<String> appliquerPromotion(
            @Parameter(description = "Code de la promotion", required = true)
            @RequestParam String code,
            @Parameter(description = "ID du joueur", required = true)
            @RequestParam Long joueurId) {
        try {
            appliquerPromotionUseCase.execute(code, joueurId);
            return ResponseEntity.ok("Promotion appliquée avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Cette classe garantit que Swagger ne demande PLUS d'ID
    public static class PromotionRequest {
        public String code;
        public java.math.BigDecimal montant;
        public LocalDateTime dateFin;
    }
}