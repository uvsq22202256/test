package com.easybet.adapters.repository.jpa;

import com.easybet.infrastructure.persistence.entity.PortefeuilleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data JPA Repository pour Portefeuille
 */
@Repository
public interface SpringDataPortefeuilleRepository extends JpaRepository<PortefeuilleEntity, Long> {
    Optional<PortefeuilleEntity> findByJoueurId(Long joueurId);
    boolean existsByJoueurId(Long joueurId);
}

