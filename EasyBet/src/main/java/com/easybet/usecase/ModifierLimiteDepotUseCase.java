package com.easybet.usecase;

import com.easybet.domain.entity.LimiteJeu;
import com.easybet.domain.repository.LimiteJeuRepository;
import com.easybet.infrastructure.event.JeuResponsableEventProducer;
import java.math.BigDecimal;

public class ModifierLimiteDepotUseCase {
    private final LimiteJeuRepository repository;
    private final JeuResponsableEventProducer eventProducer;

    public ModifierLimiteDepotUseCase(LimiteJeuRepository repository, JeuResponsableEventProducer eventProducer) {
        this.repository = repository;
        this.eventProducer = eventProducer;
    }

    public LimiteJeu execute(String limiteId, BigDecimal nouveauMontant) {
        LimiteJeu limite = repository.findById(limiteId)
                .orElseThrow(() -> new RuntimeException("Limite introuvable"));

        BigDecimal ancienMontant = limite.getLimiteDepotHebdomadaire();

        limite.modifierLimite(nouveauMontant);
        LimiteJeu saved = repository.save(limite);

        eventProducer.publishLimiteModifiee(limiteId, limite.getJoueurId(), ancienMontant, nouveauMontant);
        return saved;
    }
}