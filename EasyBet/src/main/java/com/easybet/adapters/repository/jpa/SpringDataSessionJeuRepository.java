package com.easybet.adapters.repository.jpa;

import com.easybet.infrastructure.persistence.entity.SessionJeuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataSessionJeuRepository extends JpaRepository<SessionJeuEntity, String> {

    // Exemple : Retrouver toutes les sessions d'un joueur
    List<SessionJeuEntity> findByJoueurId(String joueurId);
}