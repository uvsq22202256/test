package com.easybet.usecase;

import com.easybet.domain.entity.Promotion;
import com.easybet.repository.PromotionRepository;
import java.math.BigDecimal; // IMPORTANT : Pour gérer l'argent des pros
import java.time.LocalDate;
import java.util.Optional;

public class AppliquerPromotionUseCase {

    private final PromotionRepository promotionRepository;
    private final AjouterBonusUseCase ajouterBonusUseCase;

    public AppliquerPromotionUseCase(PromotionRepository promotionRepository,
                                     AjouterBonusUseCase ajouterBonusUseCase) {
        this.promotionRepository = promotionRepository;
        this.ajouterBonusUseCase = ajouterBonusUseCase;
    }

    public void execute(String code, Long joueurId) {
        // 1. Chercher la promo
        Optional<Promotion> promoOpt = promotionRepository.findByCode(code);

        if (promoOpt.isEmpty()) {
            throw new RuntimeException("Code promotionnel invalide.");
        }

        Promotion promo = promoOpt.get();

        // 2. Vérifier la date
        if (promo.getDateFin().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ce code promotionnel est expiré.");
        }

        // --- CORRECTION ---

        // 3. Convertir le Double en BigDecimal (pour que ton ami soit content)
        BigDecimal montant = BigDecimal.valueOf(promo.getMontant());

        // 4. Appeler la fonction avec les 4 arguments obligatoires
        // (JoueurID, Montant, "Type", "Description")
        ajouterBonusUseCase.execute(joueurId, montant, "PROMOTION", "Code: " + code);

        System.out.println("Succès : Le code " + code + " a été appliqué.");
    }
}