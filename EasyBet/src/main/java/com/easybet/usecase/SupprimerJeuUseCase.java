package com.easybet.usecase;

import com.easybet.domain.repository.JeuRepository;
import com.easybet.infrastructure.event.JeuEventProducer;

public class SupprimerJeuUseCase {
    private final JeuRepository jeuRepository;
    private final JeuEventProducer eventProducer;

    public SupprimerJeuUseCase(JeuRepository jeuRepository, JeuEventProducer eventProducer) {
        this.jeuRepository = jeuRepository;
        this.eventProducer = eventProducer;
    }

    public void execute(String id) {
        if (!jeuRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : Jeu introuvable avec l'ID " + id);
        }

        jeuRepository.deleteById(id);
        eventProducer.publishJeuSupprime(id);
    }
}