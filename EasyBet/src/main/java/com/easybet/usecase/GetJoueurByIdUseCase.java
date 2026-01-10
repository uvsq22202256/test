package com.easybet.usecase;

import com.easybet.domain.entity.Joueur;
import com.easybet.domain.repository.JoueurRepository;

public class GetJoueurByIdUseCase {

    private final JoueurRepository repository;

    public GetJoueurByIdUseCase(JoueurRepository repository) {
        this.repository = repository;
    }

    public Joueur execute(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }

        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouve avec ID : " + id));
    }
}