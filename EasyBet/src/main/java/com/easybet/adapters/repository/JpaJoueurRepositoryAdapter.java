package com.easybet.adapters.repository;

import com.easybet.adapters.repository.jpa.SpringDataJoueurRepository;
import com.easybet.domain.entity.Joueur;
import com.easybet.domain.repository.JoueurRepository;
import com.easybet.infrastructure.persistence.entity.JoueurEntity;
import com.easybet.infrastructure.persistence.mapper.JoueurMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implémentation du repository domain avec Spring Data JPA
 * Adapters Layer - Implémente l'interface du domain
 */
@Component
public class JpaJoueurRepositoryAdapter implements JoueurRepository {

    private final SpringDataJoueurRepository springDataRepository;
    private final JoueurMapper mapper;

    public JpaJoueurRepositoryAdapter(SpringDataJoueurRepository springDataRepository, JoueurMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Joueur save(Joueur joueur) {
        JoueurEntity entity = mapper.toEntity(joueur);
        JoueurEntity savedEntity = springDataRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Joueur> findById(Long id) {
        return springDataRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<Joueur> findAll() {
        return springDataRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return springDataRepository.existsById(id);
    }

    @Override
    public Optional<Joueur> findByPseudo(String pseudo) {
        return springDataRepository.findByPseudo(pseudo)
                .map(mapper::toDomain);
    }
}

