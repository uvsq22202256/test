package com.easybet.adapters.repository.jpa;

import com.easybet.infrastructure.persistence.entity.LimiteJeuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataLimiteJeuRepository extends JpaRepository<LimiteJeuEntity, String> {
    Optional<LimiteJeuEntity> findByJoueurId(String joueurId);
    boolean existsByJoueurId(String joueurId);
}