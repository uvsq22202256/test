package com.easybet.repository;

import com.easybet.domain.entity.Promotion;
import java.util.List;
import java.util.Optional;

public interface PromotionRepository {

    // Sauvegarder
    Promotion save(Promotion promotion);

    // Trouver par code
    Optional<Promotion> findByCode(String code);

    // Tout lister
    List<Promotion> findAll();

    // Supprimer
    void deleteById(Long id);
}