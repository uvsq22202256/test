package com.easybet.usecase;

import com.easybet.domain.entity.Jeu;
import com.easybet.domain.repository.JeuRepository;
import java.math.BigDecimal;
import java.util.UUID;

public class CreerJeuUseCase {
    private final JeuRepository jeuRepository;

    public CreerJeuUseCase(JeuRepository jeuRepository) {
        this.jeuRepository = jeuRepository;
    }

    public Jeu execute(String nom, String type, BigDecimal rtp) {
        Jeu jeu = new Jeu(UUID.randomUUID().toString(), nom, type, rtp, true);
        return jeuRepository.save(jeu);
    }
}