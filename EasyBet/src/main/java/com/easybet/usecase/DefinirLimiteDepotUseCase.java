package com.easybet.usecase;

import com.easybet.domain.entity.LimiteJeu;
import com.easybet.domain.repository.LimiteJeuRepository;
import com.easybet.infrastructure.event.JeuResponsableEventProducer;
import java.math.BigDecimal;
import java.util.UUID;

public class DefinirLimiteDepotUseCase {
    private final LimiteJeuRepository repository;
    private final JeuResponsableEventProducer eventProducer;

    public DefinirLimiteDepotUseCase(LimiteJeuRepository repository, JeuResponsableEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    public LimiteJeu execute(String joueurId, BigDecimal montant) {
        if (repository.existsByJoueurId(joueurId)) {
            throw new RuntimeException("Ce joueur a déjà une limite active. Utilisez la modification.");
        }

        LimiteJeu limite = new LimiteJeu(UUID.randomUUID().toString(), joueurId, montant);
        LimiteJeu saved = repository.save(limite);

        eventProducer.publishLimiteDefinie(saved.getId(), joueurId, montant);
        return saved;
    }
}