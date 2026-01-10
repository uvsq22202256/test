package com.easybet.adapters.repository;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.infrastructure.persistence.entity.PortefeuilleEntity;
import com.easybet.infrastructure.persistence.mapper.PortefeuilleMapper;
import com.easybet.adapters.repository.jpa.SpringDataPortefeuilleRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptateur Repository Portefeuille
 * Implémente l'interface domain avec Spring Data JPA
 */
@Component
public class JpaPortefeuilleRepositoryAdapter implements PortefeuilleRepository {

    private final SpringDataPortefeuilleRepository springDataRepository;

    public JpaPortefeuilleRepositoryAdapter(SpringDataPortefeuilleRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Portefeuille save(Portefeuille portefeuille) {
        PortefeuilleEntity entity = PortefeuilleMapper.toEntity(portefeuille);
        PortefeuilleEntity savedEntity = springDataRepository.save(entity);
        return PortefeuilleMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Portefeuille> findById(Long id) {
        return springDataRepository.findById(id)
                .map(PortefeuilleMapper::toDomain);
    }

    @Override
    public Optional<Portefeuille> findByJoueurId(Long joueurId) {
        return springDataRepository.findByJoueurId(joueurId)
                .map(PortefeuilleMapper::toDomain);
    }

    @Override
    public List<Portefeuille> findAll() {
        return springDataRepository.findAll()
                .stream()
                .map(PortefeuilleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public boolean existsByJoueurId(Long joueurId) {
        return springDataRepository.existsByJoueurId(joueurId);
    }

    @Override
    public Portefeuille createPortefeuilleForNewJoueur(Long joueurId, String pseudo, java.math.BigDecimal soldeInitial) {
        Portefeuille portefeuille = new Portefeuille(joueurId, pseudo, soldeInitial, java.math.BigDecimal.ZERO);
        return save(portefeuille);
    }
}

