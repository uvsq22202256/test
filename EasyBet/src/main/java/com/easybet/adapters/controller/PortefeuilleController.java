package com.easybet.adapters.controller;

import com.easybet.adapters.dto.*;
import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.entity.Transaction;
import com.easybet.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST - Gestion des Portefeuilles
 * Ressource orientée : /portefeuilles
 * Adapters Layer
 */
@RestController
@RequestMapping("/portefeuilles")
@Tag(name = "Portefeuilles", description = "API RESTful de gestion des portefeuilles")
public class PortefeuilleController {

    private final GetPortefeuilleUseCase getPortefeuilleUseCase;
    private final BloquerPortefeuilleUseCase bloquerPortefeuilleUseCase;
    private final DebloquerPortefeuilleUseCase debloquerPortefeuilleUseCase;
    private final SupprimerPortefeuilleUseCase supprimerPortefeuilleUseCase;

    public PortefeuilleController(GetPortefeuilleUseCase getPortefeuilleUseCase,
                                 BloquerPortefeuilleUseCase bloquerPortefeuilleUseCase,
                                 DebloquerPortefeuilleUseCase debloquerPortefeuilleUseCase,
                                 SupprimerPortefeuilleUseCase supprimerPortefeuilleUseCase) {
        this.getPortefeuilleUseCase = getPortefeuilleUseCase;
        this.bloquerPortefeuilleUseCase = bloquerPortefeuilleUseCase;
        this.debloquerPortefeuilleUseCase = debloquerPortefeuilleUseCase;
        this.supprimerPortefeuilleUseCase = supprimerPortefeuilleUseCase;
    }

    /**
     * GET /api/portefeuilles?joueurId=1
     * Récupérer le portefeuille d'un joueur
     */
    @GetMapping
    @Operation(summary = "Récupérer un portefeuille",
               description = "Retourne le portefeuille d'un joueur spécifique")
    @ApiResponse(responseCode = "200", description = "Portefeuille récupéré avec succès")
    public ResponseEntity<PortefeuilleResponseDTO> getPortefeuille(
            @Parameter(description = "ID du joueur", required = true)
            @RequestParam Long joueurId) {

        Portefeuille portefeuille = getPortefeuilleUseCase.execute(joueurId);
        return ResponseEntity.ok(PortefeuilleResponseDTO.fromDomain(portefeuille));
    }

    /**
     * GET /api/portefeuilles/{portefeuilleId}
     * Récupérer les détails d'un portefeuille spécifique
     */
    @GetMapping("/{portefeuilleId}")
    @Operation(summary = "Récupérer les détails d'un portefeuille",
               description = "Retourne les détails complets d'un portefeuille")
    @ApiResponse(responseCode = "200", description = "Portefeuille récupéré avec succès")
    public ResponseEntity<PortefeuilleResponseDTO> getPortefeuilleById(
            @Parameter(description = "ID du portefeuille", required = true)
            @PathVariable Long portefeuilleId) {

        Portefeuille portefeuille = getPortefeuilleUseCase.execute(portefeuilleId);
        return ResponseEntity.ok(PortefeuilleResponseDTO.fromDomain(portefeuille));
    }

    /**
     * PATCH /api/portefeuilles/{portefeuilleId}
     * Modifier l'état du portefeuille (bloquer/débloquer)
     */
    @PatchMapping("/{portefeuilleId}")
    @Operation(summary = "Modifier l'état du portefeuille",
               description = "Bloque ou débloque un portefeuille")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Portefeuille modifié avec succès"),
            @ApiResponse(responseCode = "404", description = "Portefeuille non trouvé")
    })
    public ResponseEntity<PortefeuilleResponseDTO> modifierEtatPortefeuille(
            @Parameter(description = "ID du portefeuille", required = true)
            @PathVariable Long portefeuilleId,
            @Valid @RequestBody PortefeuilleStatutRequestDTO request) {

        Portefeuille portefeuille;

        if ("BLOQUE".equals(request.getStatut())) {
            portefeuille = bloquerPortefeuilleUseCase.execute(
                    portefeuilleId,
                    request.getRaison(),
                    request.getDateDeblocage()
            );
        } else if ("ACTIF".equals(request.getStatut())) {
            portefeuille = debloquerPortefeuilleUseCase.execute(portefeuilleId);
        } else {
            throw new RuntimeException("Statut invalide. Utilisez ACTIF ou BLOQUE");
        }

        return ResponseEntity.ok(PortefeuilleResponseDTO.fromDomain(portefeuille));
    }

    /**
     * DELETE /portefeuilles/{portefeuilleId}
     * Supprimer le portefeuille
     */
    @DeleteMapping("/{portefeuilleId}")
    @Operation(summary = "Supprimer un portefeuille",
               description = "Supprime complètement un portefeuille")
    @ApiResponse(responseCode = "204", description = "Portefeuille supprimé avec succès")
    public ResponseEntity<Void> supprimerPortefeuille(
            @Parameter(description = "ID du portefeuille", required = true)
            @PathVariable Long portefeuilleId) {

        supprimerPortefeuilleUseCase.execute(portefeuilleId);
        return ResponseEntity.noContent().build();
    }
}

