package com.easybet.adapters.repository.jpa;

import com.easybet.infrastructure.persistence.entity.JoueurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataJoueurRepository extends JpaRepository<JoueurEntity, Long> {

    Optional<JoueurEntity> findByPseudo(String pseudo);
}