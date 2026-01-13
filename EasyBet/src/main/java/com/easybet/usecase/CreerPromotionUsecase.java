package com.easybet.usecase;

import com.easybet.domain.entity.Promotion;
import com.easybet.repository.PromotionRepository;

public class CreerPromotionUsecase {
    private final PromotionRepository promotionRepository;

    public CreerPromotionUsecase(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Promotion execute(Promotion promotion) {
        return promotionRepository.save(promotion);
    }
}