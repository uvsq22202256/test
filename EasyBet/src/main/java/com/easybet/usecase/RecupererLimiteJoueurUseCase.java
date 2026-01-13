package com.easybet.usecase;

import com.easybet.domain.entity.LimiteJeu;
import com.easybet.domain.repository.LimiteJeuRepository;

public class RecupererLimiteJoueurUseCase {
    private final LimiteJeuRepository repository;

    public RecupererLimiteJoueurUseCase(LimiteJeuRepository repository) {
        this.repository = repository;
    }

    public LimiteJeu execute(String joueurId) {
        return repository.findByJoueurId(joueurId)
                .orElseThrow(() -> new RuntimeException("Aucune limite trouvée pour ce joueur"));
    }
}