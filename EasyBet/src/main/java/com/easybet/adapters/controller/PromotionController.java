package com.easybet.adapters.controller;

import com.easybet.domain.entity.Promotion;
import com.easybet.usecase.AppliquerPromotionUseCase;
import com.easybet.usecase.CreerPromotionUsecase;
import com.easybet.usecase.RecupererPromotionActivesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/promotions")
@Tag(name = "Promotions", description = "API de gestion des bonus")
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
    @Operation(summary = "Créer une promotion (Admin)")
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
    public ResponseEntity<List<Promotion>> listerPromotionsActives() {
        return ResponseEntity.ok(recupererActivesUseCase.execute());
    }

    @PostMapping("/appliquer")
    public ResponseEntity<String> appliquerPromotion(@RequestParam String code, @RequestParam Long joueurId) {
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