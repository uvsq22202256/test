package com.easybet.adapters.repository;

import com.easybet.adapters.repository.jpa.SpringDataLimiteJeuRepository;
import com.easybet.domain.entity.LimiteJeu;
import com.easybet.domain.repository.LimiteJeuRepository;
import com.easybet.infrastructure.persistence.entity.LimiteJeuEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaLimiteJeuRepositoryAdapter implements LimiteJeuRepository {

    private final SpringDataLimiteJeuRepository repository;

    public JpaLimiteJeuRepositoryAdapter(SpringDataLimiteJeuRepository repository) {
        this.repository = repository;
    }

    @Override
    public LimiteJeu save(LimiteJeu limite) {
        LimiteJeuEntity entity = new LimiteJeuEntity();
        entity.id = limite.getId();
        entity.joueurId = limite.getJoueurId();
        entity.limiteDepotHebdomadaire = limite.getLimiteDepotHebdomadaire();

        repository.save(entity);
        return limite;
    }

    @Override
    public Optional<LimiteJeu> findByJoueurId(String joueurId) {
        return repository.findByJoueurId(joueurId)
                .map(e -> new LimiteJeu(e.id, e.joueurId, e.limiteDepotHebdomadaire));
    }

    @Override
    public Optional<LimiteJeu> findById(String id) {
        return repository.findById(id)
                .map(e -> new LimiteJeu(e.id, e.joueurId, e.limiteDepotHebdomadaire));
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public boolean existsByJoueurId(String joueurId) {
        return repository.existsByJoueurId(joueurId);
    }
}