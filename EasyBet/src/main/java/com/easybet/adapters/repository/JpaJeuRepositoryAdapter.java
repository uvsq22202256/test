package com.easybet.adapters.repository;

import com.easybet.adapters.repository.jpa.SpringDataJeuRepository;
import com.easybet.domain.entity.Jeu;
import com.easybet.domain.repository.JeuRepository;
import com.easybet.infrastructure.persistence.entity.JeuEntity;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaJeuRepositoryAdapter implements JeuRepository {
    private final SpringDataJeuRepository repository;

    public JpaJeuRepositoryAdapter(SpringDataJeuRepository repository) {
        this.repository = repository;
    }

    @Override
    public Jeu save(Jeu jeu) {
        JeuEntity entity = new JeuEntity();
        entity.id = jeu.getId();
        entity.nom = jeu.getNom();
        entity.type = jeu.getType();
        entity.tauxRistourne = jeu.getTauxRistourne();
        entity.actif = jeu.isActif();
        repository.save(entity);
        return jeu;
    }

    @Override
    public Optional<Jeu> findById(String id) {
        return repository.findById(id).map(e -> new Jeu(e.id, e.nom, e.type, e.tauxRistourne, e.actif));
    }

    @Override
    public List<Jeu> findAll() {
        return repository.findAll().stream()
                .map(e -> new Jeu(e.id, e.nom, e.type, e.tauxRistourne, e.actif))
                .collect(Collectors.toList());
    }
}