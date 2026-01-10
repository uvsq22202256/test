package com.easybet.domain.repository;

import com.easybet.domain.entity.Joueur;

import java.util.List;
import java.util.Optional;

/**
 * Interface repository pour Joueur - Domain Layer
 * SANS annotations Spring (Clean Architecture)
 * Implémentée par l'infrastructure layer
 */
public interface JoueurRepository {

    /**
     * Sauvegarde un joueur (création ou mise à jour)
     */
    Joueur save(Joueur joueur);

    /**
     * Récupère un joueur par son ID
     */
    Optional<Joueur> findById(Long id);

    /**
     * Récupère tous les joueurs
     */
    List<Joueur> findAll();

    /**
     * Supprime un joueur par son ID
     */
    void deleteById(Long id);

    /**
     * Vérifie si un joueur existe par son ID
     */
    boolean existsById(Long id);

    /**
     * Trouve un joueur par son pseudo
     */
    Optional<Joueur> findByPseudo(String pseudo);
}

