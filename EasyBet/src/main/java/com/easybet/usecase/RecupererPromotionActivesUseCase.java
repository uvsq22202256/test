package com.easybet.usecase;

import com.easybet.domain.entity.Promotion;
import com.easybet.repository.PromotionRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class RecupererPromotionActivesUseCase {
    private final PromotionRepository promotionRepository;

    public RecupererPromotionActivesUseCase(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<Promotion> execute() {
        return promotionRepository.findAll().stream()
                .filter(p -> p.getDateFin() != null && p.getDateFin().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
    }
}