package com.easybet.adapters.controller;

import com.easybet.adapters.dto.*;
import com.easybet.domain.entity.Jeu;
import com.easybet.domain.entity.SessionJeu;
import com.easybet.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jeux")
@Tag(name = "Jeux", description = "API Jeux et Casino")
public class JeuController {

    private final CreerJeuUseCase creerJeuUseCase;
    private final ListerJeuxUseCase listerJeuxUseCase;
    private final ModifierJeuUseCase modifierJeuUseCase; // <--- Nouveau
    private final SupprimerJeuUseCase supprimerJeuUseCase;
    private final DemarrerSessionUseCase demarrerSessionUseCase;
    private final TerminerSessionUseCase terminerSessionUseCase;

    public JeuController(CreerJeuUseCase creerJeuUseCase,
                         ListerJeuxUseCase listerJeuxUseCase,
                         ModifierJeuUseCase modifierJeuUseCase, // <--- Nouveau
                         SupprimerJeuUseCase supprimerJeuUseCase,
                         DemarrerSessionUseCase demarrerSessionUseCase,
                         TerminerSessionUseCase terminerSessionUseCase) {
        this.creerJeuUseCase = creerJeuUseCase;
        this.listerJeuxUseCase = listerJeuxUseCase;
        this.modifierJeuUseCase = modifierJeuUseCase; // <--- Nouveau
        this.supprimerJeuUseCase = supprimerJeuUseCase;
        this.demarrerSessionUseCase = demarrerSessionUseCase;
        this.terminerSessionUseCase = terminerSessionUseCase;
    }

    @PostMapping
    @Operation(summary = "Créer un jeu", description = "Ajoute un nouveau jeu au catalogue avec son nom, type et RTP (Return To Player)")
    @ApiResponse(responseCode = "200", description = "Jeu créé avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<JeuResponseDTO> creerJeu(@RequestBody JeuRequestDTO request) {
        Jeu jeu = creerJeuUseCase.execute(request.getNom(), request.getType(), request.getRtp());
        return ResponseEntity.ok(JeuResponseDTO.fromDomain(jeu));
    }

    @GetMapping
    @Operation(summary = "Lister les jeux", description = "Retourne la liste complète de tous les jeux disponibles dans le catalogue")
    @ApiResponse(responseCode = "200", description = "Liste des jeux récupérée avec succès")
    public ResponseEntity<List<JeuResponseDTO>> listerJeux() {
        List<JeuResponseDTO> jeux = listerJeuxUseCase.execute().stream()
                .map(JeuResponseDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jeux);
    }

    // --- NOUVEAU ENDPOINT PUT ---
    @PutMapping("/{id}")
    @Operation(summary = "Modifier un jeu", description = "Met à jour les informations d'un jeu existant (Nom, Type, RTP)")
    @ApiResponse(responseCode = "200", description = "Jeu modifié avec succès")
    @ApiResponse(responseCode = "404", description = "Jeu non trouvé")
    public ResponseEntity<JeuResponseDTO> modifierJeu(
            @Parameter(description = "ID du jeu à modifier", required = true) @PathVariable String id,
            @RequestBody JeuRequestDTO request) {

        Jeu jeu = modifierJeuUseCase.execute(id, request.getNom(), request.getType(), request.getRtp());
        return ResponseEntity.ok(JeuResponseDTO.fromDomain(jeu));
    }
    // -----------------------------

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un jeu", description = "Supprime définitivement un jeu du catalogue par son ID")
    @ApiResponse(responseCode = "204", description = "Jeu supprimé avec succès")
    @ApiResponse(responseCode = "404", description = "Jeu non trouvé")
    public ResponseEntity<Void> supprimerJeu(
            @Parameter(description = "ID du jeu à supprimer", required = true) @PathVariable String id) {
        supprimerJeuUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/session/demarrer")
    @Operation(summary = "Démarrer une session (Miser)", description = "Le joueur place une mise sur un jeu et démarre une session de jeu")
    @ApiResponse(responseCode = "200", description = "Session démarrée avec succès")
    @ApiResponse(responseCode = "400", description = "Mise invalide ou solde insuffisant")
    @ApiResponse(responseCode = "404", description = "Joueur ou jeu non trouvé")
    public ResponseEntity<SessionJeuResponseDTO> demarrerSession(@RequestBody SessionRequestDTO request) {
        SessionJeu session = demarrerSessionUseCase.execute(
                request.getJoueurId(), request.getJeuId(), request.getMise()
        );
        return ResponseEntity.ok(SessionJeuResponseDTO.fromDomain(session));
    }

    @PostMapping("/session/{id}/terminer")
    @Operation(summary = "Terminer une session (Résultat)", description = "Termine une session de jeu et calcule le gain ou la perte du joueur")
    @ApiResponse(responseCode = "200", description = "Session terminée avec succès")
    @ApiResponse(responseCode = "404", description = "Session non trouvée")
    public ResponseEntity<SessionJeuResponseDTO> terminerSession(
            @Parameter(description = "ID de la session à terminer", required = true) @PathVariable String id) {
        SessionJeu session = terminerSessionUseCase.execute(id);
        return ResponseEntity.ok(SessionJeuResponseDTO.fromDomain(session));
    }
}