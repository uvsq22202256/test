package com.easybet.usecase;

import com.easybet.domain.entity.Jeu;
import com.easybet.domain.repository.JeuRepository;
import java.util.List;

public class ListerJeuxUseCase {
    private final JeuRepository jeuRepository;

    public ListerJeuxUseCase(JeuRepository jeuRepository) {
        this.jeuRepository = jeuRepository;
    }

    public List<Jeu> execute() {
        return jeuRepository.findAll();
    }
}