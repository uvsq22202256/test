package com.easybet.usecase;

import com.easybet.domain.entity.Jeu;
import com.easybet.domain.repository.JeuRepository;
import com.easybet.infrastructure.event.JeuEventProducer;
import java.math.BigDecimal;
import java.util.UUID;

public class CreerJeuUseCase {
    private final JeuRepository jeuRepository;
    private final JeuEventProducer eventProducer; // Ajout

    public CreerJeuUseCase(JeuRepository jeuRepository, JeuEventProducer eventProducer) {
        this.jeuRepository = jeuRepository;
        this.eventProducer = eventProducer;
    }

    public Jeu execute(String nom, String type, BigDecimal rtp) {
        Jeu jeu = new Jeu(UUID.randomUUID().toString(), nom, type, rtp, true);
        Jeu savedJeu = jeuRepository.save(jeu);

        // Envoi de l'event
        eventProducer.publishJeuCree(savedJeu.getId(), savedJeu.getNom(), savedJeu.getType(), savedJeu.getTauxRistourne());

        return savedJeu;
    }
}