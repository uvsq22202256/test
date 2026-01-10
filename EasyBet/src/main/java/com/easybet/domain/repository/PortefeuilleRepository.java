package com.easybet.domain.repository;

import com.easybet.domain.entity.Portefeuille;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Interface Repository pour Portefeuille
 * Domain Layer - Définit le contrat pour accès aux données
 */
public interface PortefeuilleRepository {

    Portefeuille save(Portefeuille portefeuille);

    Optional<Portefeuille> findById(Long id);

    Optional<Portefeuille> findByJoueurId(Long joueurId);

    List<Portefeuille> findAll();

    void deleteById(Long id);

    boolean existsByJoueurId(Long joueurId);

    /**
     * Crée un portefeuille initial pour un nouveau joueur
     */
    Portefeuille createPortefeuilleForNewJoueur(Long joueurId, String pseudo, BigDecimal soldeInitial);
}

