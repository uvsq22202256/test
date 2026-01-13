package com.easybet.domain.repository;

import com.easybet.domain.entity.Jeu;
import java.util.List;
import java.util.Optional;

public interface JeuRepository {
    Jeu save(Jeu jeu);
    Optional<Jeu> findById(String id);
    List<Jeu> findAll();
}