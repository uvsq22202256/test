package com.easybet.adapters.controller;

import com.easybet.adapters.dto.JoueurRequestDTO;
import com.easybet.adapters.dto.JoueurResponseDTO;
import com.easybet.domain.entity.Joueur;
import com.easybet.usecase.CreateJoueurUseCase;
import com.easybet.usecase.DeleteJoueurUseCase;
import com.easybet.usecase.GetAllJoueursUseCase;
import com.easybet.usecase.GetJoueurByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller REST pour les joueurs
 * Adapters Layer
 */
@RestController
@RequestMapping("/joueurs")
@Tag(name = "Joueurs", description = "API de gestion des joueurs du casino")
public class JoueurController {

    private final CreateJoueurUseCase createJoueurUseCase;
    private final GetAllJoueursUseCase getAllJoueursUseCase;
    private final GetJoueurByIdUseCase getJoueurByIdUseCase;
    private final DeleteJoueurUseCase deleteJoueurUseCase;

    public JoueurController(
            CreateJoueurUseCase createJoueurUseCase,
            GetAllJoueursUseCase getAllJoueursUseCase,
            GetJoueurByIdUseCase getJoueurByIdUseCase,
            DeleteJoueurUseCase deleteJoueurUseCase) {
        this.createJoueurUseCase = createJoueurUseCase;
        this.getAllJoueursUseCase = getAllJoueursUseCase;
        this.getJoueurByIdUseCase = getJoueurByIdUseCase;
        this.deleteJoueurUseCase = deleteJoueurUseCase;
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau joueur", description = "Crée un nouveau joueur avec un pseudo et un email uniques")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Joueur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    public ResponseEntity<JoueurResponseDTO> createJoueur(
            @Valid @RequestBody JoueurRequestDTO request) {

        Joueur joueur = createJoueurUseCase.execute(request.getPseudo(), request.getEmail());

        // La publication Kafka est effectuée dans CreateJoueurUseCase
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JoueurResponseDTO.fromDomain(joueur));
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les joueurs", description = "Retourne la liste complète des joueurs")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    public ResponseEntity<List<JoueurResponseDTO>> getAllJoueurs() {
        List<Joueur> joueurs = getAllJoueursUseCase.execute();
        List<JoueurResponseDTO> response = joueurs.stream()
                .map(JoueurResponseDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un joueur par ID", description = "Retourne les détails d'un joueur spécifique")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Joueur trouvé"),
            @ApiResponse(responseCode = "404", description = "Joueur non trouvé")
    })
    public ResponseEntity<JoueurResponseDTO> getJoueurById(
            @Parameter(description = "ID du joueur", required = true)
            @PathVariable Long id) {

        Joueur joueur = getJoueurByIdUseCase.execute(id);
        return ResponseEntity.ok(JoueurResponseDTO.fromDomain(joueur));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un joueur", description = "Supprime un joueur du système")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Joueur supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Joueur non trouvé")
    })
    public ResponseEntity<Void> deleteJoueur(
            @Parameter(description = "ID du joueur à supprimer", required = true)
            @PathVariable Long id) {

        deleteJoueurUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
