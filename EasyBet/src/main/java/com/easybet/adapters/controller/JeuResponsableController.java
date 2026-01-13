package com.easybet.adapters.controller;

import com.easybet.adapters.dto.LimiteJeuRequestDTO;
import com.easybet.adapters.dto.LimiteJeuResponseDTO;
import com.easybet.domain.entity.LimiteJeu;
import com.easybet.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/jeu-responsable") // <--- Changement ici : plus de "/api"
@Tag(name = "Jeu Responsable", description = "Gestion des limites de jeu et protection des joueurs")
public class JeuResponsableController {

    private final DefinirLimiteDepotUseCase definirUseCase;
    private final RecupererLimiteJoueurUseCase recupererUseCase;
    private final ModifierLimiteDepotUseCase modifierUseCase;
    private final SupprimerLimiteDepotUseCase supprimerUseCase;

    public JeuResponsableController(DefinirLimiteDepotUseCase definirUseCase,
                                    RecupererLimiteJoueurUseCase recupererUseCase,
                                    ModifierLimiteDepotUseCase modifierUseCase,
                                    SupprimerLimiteDepotUseCase supprimerUseCase) {
        this.definirUseCase = definirUseCase;
        this.recupererUseCase = recupererUseCase;
        this.modifierUseCase = modifierUseCase;
        this.supprimerUseCase = supprimerUseCase;
    }

    @PostMapping("/limites")
    @Operation(summary = "Définir une limite de dépôt", description = "Crée une limite de dépôt hebdomadaire pour un joueur.")
    public ResponseEntity<LimiteJeuResponseDTO> definirLimite(@Valid @RequestBody LimiteJeuRequestDTO request) {
        LimiteJeu limite = definirUseCase.execute(request.getJoueurId(), request.getMontantLimite());
        return ResponseEntity.ok(LimiteJeuResponseDTO.fromDomain(limite));
    }

    @GetMapping("/limites/joueur/{joueurId}")
    @Operation(summary = "Voir la limite d'un joueur", description = "Récupère la configuration de limite active pour un joueur.")
    public ResponseEntity<LimiteJeuResponseDTO> getLimite(@PathVariable String joueurId) {
        LimiteJeu limite = recupererUseCase.execute(joueurId);
        return ResponseEntity.ok(LimiteJeuResponseDTO.fromDomain(limite));
    }

    @PutMapping("/limites/{id}")
    @Operation(summary = "Modifier une limite", description = "Met à jour le montant d'une limite existante.")
    public ResponseEntity<LimiteJeuResponseDTO> modifierLimite(
            @Parameter(description = "ID de la limite") @PathVariable String id,
            @RequestBody LimiteJeuRequestDTO request) { // Réutilisation du DTO Request pour le montant

        // On suppose que le montant est dans le DTO, le joueurId est ignoré pour la modif
        LimiteJeu limite = modifierUseCase.execute(id, request.getMontantLimite());
        return ResponseEntity.ok(LimiteJeuResponseDTO.fromDomain(limite));
    }

    @DeleteMapping("/limites/{id}")
    @Operation(summary = "Supprimer une limite", description = "Supprime complètement la restriction de dépôt pour le joueur.")
    public ResponseEntity<Void> supprimerLimite(@PathVariable String id) {
        supprimerUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}