package com.easybet.adapters.controller;

import com.easybet.adapters.dto.*;
import com.easybet.domain.entity.Jeu;
import com.easybet.domain.entity.SessionJeu;
import com.easybet.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
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
    private final DemarrerSessionUseCase demarrerSessionUseCase;
    private final TerminerSessionUseCase terminerSessionUseCase;

    public JeuController(CreerJeuUseCase creerJeuUseCase,
                         ListerJeuxUseCase listerJeuxUseCase,
                         DemarrerSessionUseCase demarrerSessionUseCase,
                         TerminerSessionUseCase terminerSessionUseCase) {
        this.creerJeuUseCase = creerJeuUseCase;
        this.listerJeuxUseCase = listerJeuxUseCase;
        this.demarrerSessionUseCase = demarrerSessionUseCase;
        this.terminerSessionUseCase = terminerSessionUseCase;
    }

    @PostMapping
    @Operation(summary = "Créer un jeu")
    public ResponseEntity<JeuResponseDTO> creerJeu(@RequestBody JeuRequestDTO request) {
        Jeu jeu = creerJeuUseCase.execute(request.getNom(), request.getType(), request.getRtp());
        return ResponseEntity.ok(JeuResponseDTO.fromDomain(jeu));
    }

    @GetMapping
    @Operation(summary = "Lister les jeux")
    public ResponseEntity<List<JeuResponseDTO>> listerJeux() {
        List<JeuResponseDTO> jeux = listerJeuxUseCase.execute().stream()
                .map(JeuResponseDTO::fromDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(jeux);
    }

    @PostMapping("/session/demarrer")
    @Operation(summary = "Miser et jouer (Démarrer)")
    public ResponseEntity<SessionJeuResponseDTO> demarrerSession(@RequestBody SessionRequestDTO request) {
        SessionJeu session = demarrerSessionUseCase.execute(
                request.getJoueurId(), request.getJeuId(), request.getMise()
        );
        return ResponseEntity.ok(SessionJeuResponseDTO.fromDomain(session));
    }

    @PostMapping("/session/{id}/terminer")
    @Operation(summary = "Obtenir le résultat (Terminer)")
    public ResponseEntity<SessionJeuResponseDTO> terminerSession(@PathVariable String id) {
        // Plus de paramètre de gain ici, le backend décide !
        SessionJeu session = terminerSessionUseCase.execute(id);
        return ResponseEntity.ok(SessionJeuResponseDTO.fromDomain(session));
    }
}