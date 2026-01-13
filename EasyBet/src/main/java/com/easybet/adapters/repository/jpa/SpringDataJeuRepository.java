package com.easybet.adapters.repository.jpa;

import com.easybet.infrastructure.persistence.entity.JeuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJeuRepository extends JpaRepository<JeuEntity, String> {
    // Méthodes de recherche personnalisées si besoin (ex: findByNom)
}