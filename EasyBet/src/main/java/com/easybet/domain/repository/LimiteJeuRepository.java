package com.easybet.domain.repository;

import com.easybet.domain.entity.LimiteJeu;
import java.util.Optional;

public interface LimiteJeuRepository {
    LimiteJeu save(LimiteJeu limite);
    Optional<LimiteJeu> findByJoueurId(String joueurId);
    Optional<LimiteJeu> findById(String id);
    void deleteById(String id);
    boolean existsByJoueurId(String joueurId);
}