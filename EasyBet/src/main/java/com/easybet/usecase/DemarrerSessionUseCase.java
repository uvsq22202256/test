package com.easybet.usecase;

import com.easybet.domain.entity.SessionJeu;
import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.repository.JeuRepository;
import com.easybet.domain.repository.SessionJeuRepository;
import com.easybet.domain.repository.PortefeuilleRepository;
import java.math.BigDecimal;
import java.util.UUID;

public class DemarrerSessionUseCase {
    private final SessionJeuRepository sessionRepository;
    private final JeuRepository jeuRepository;
    private final PortefeuilleRepository portefeuilleRepository;
    private final EffectuerRetraitUseCase retraitUseCase;

    public DemarrerSessionUseCase(SessionJeuRepository sessionRepository,
                                  JeuRepository jeuRepository,
                                  PortefeuilleRepository portefeuilleRepository,
                                  EffectuerRetraitUseCase retraitUseCase) {
        this.sessionRepository = sessionRepository;
        this.jeuRepository = jeuRepository;
        this.portefeuilleRepository = portefeuilleRepository;
        this.retraitUseCase = retraitUseCase;
    }

    public SessionJeu execute(String joueurId, String jeuId, BigDecimal mise) {
        if (jeuRepository.findById(jeuId).isEmpty()) {
            throw new RuntimeException("Jeu introuvable : " + jeuId);
        }

        Long joueurIdLong;
        try {
            joueurIdLong = Long.valueOf(joueurId);
        } catch (NumberFormatException e) {
            throw new RuntimeException("ID Joueur invalide : " + joueurId);
        }

        Portefeuille portefeuille = portefeuilleRepository.findByJoueurId(joueurIdLong)
                .orElseThrow(() -> new RuntimeException("Portefeuille introuvable"));

        // Débit de la mise (sécurisé)
        retraitUseCase.execute(portefeuille.getId(), mise, "MISE_JEU", "Mise jeu " + jeuId);

        SessionJeu session = SessionJeu.creer(UUID.randomUUID().toString(), joueurId, jeuId, mise);
        return sessionRepository.save(session);
    }
}