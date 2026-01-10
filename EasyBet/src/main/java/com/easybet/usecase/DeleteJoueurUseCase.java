package com.easybet.usecase;

import com.easybet.domain.repository.JoueurRepository;
import com.easybet.infrastructure.event.JoueurEventProducer;

public class DeleteJoueurUseCase {

    private final JoueurRepository repository;
    private final JoueurEventProducer eventProducer;

    public DeleteJoueurUseCase(JoueurRepository repository, JoueurEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    public void execute(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID invalide");
        }

        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Joueur non trouve avec ID : " + id);
        }

        repository.deleteById(id);

        // Publier l'événement de suppression
        eventProducer.publishJoueurSupprimeEvent(id);
    }
}