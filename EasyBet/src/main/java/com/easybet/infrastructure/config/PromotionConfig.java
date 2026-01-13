package com.easybet.infrastructure.config;

import com.easybet.repository.PromotionRepository;
import com.easybet.usecase.AjouterBonusUseCase;
import com.easybet.usecase.AppliquerPromotionUseCase;
import com.easybet.usecase.CreerPromotionUsecase;
import com.easybet.usecase.RecupererPromotionActivesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PromotionConfig {
    @Bean
    public CreerPromotionUsecase creerPromotionUsecase(PromotionRepository repo) {
        return new CreerPromotionUsecase(repo);
    }
    @Bean
    public RecupererPromotionActivesUseCase recupererPromotionActivesUseCase(PromotionRepository repo) {
        return new RecupererPromotionActivesUseCase(repo);
    }
    @Bean
    public AppliquerPromotionUseCase appliquerPromotionUseCase(PromotionRepository promoRepo, AjouterBonusUseCase bonusUseCase) {
        return new AppliquerPromotionUseCase(promoRepo, bonusUseCase);
    }
}