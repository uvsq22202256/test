package com.easybet.usecase;

import com.easybet.domain.entity.LimiteJeu;
import com.easybet.domain.repository.LimiteJeuRepository;
import com.easybet.infrastructure.event.JeuResponsableEventProducer;

public class SupprimerLimiteDepotUseCase {
    private final LimiteJeuRepository repository;
    private final JeuResponsableEventProducer eventProducer;

    public SupprimerLimiteDepotUseCase(LimiteJeuRepository repository, JeuResponsableEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    public void execute(String limiteId) {
        LimiteJeu limite = repository.findById(limiteId)
                .orElseThrow(() -> new RuntimeException("Limite introuvable"));

        repository.deleteById(limiteId);
        eventProducer.publishLimiteSupprimee(limiteId, limite.getJoueurId());
    }
}