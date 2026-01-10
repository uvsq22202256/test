package com.easybet.usecase;

import com.easybet.domain.entity.Joueur;
import com.easybet.domain.repository.JoueurRepository;

import java.util.List;

public class GetAllJoueursUseCase {

    private final JoueurRepository repository;

    public GetAllJoueursUseCase(JoueurRepository repository) {
        this.repository = repository;
    }

    public List<Joueur> execute() {
        return repository.findAll();
    }
}