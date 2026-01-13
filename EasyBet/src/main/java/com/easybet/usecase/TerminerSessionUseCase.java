package com.easybet.usecase;

import com.easybet.domain.entity.SessionJeu;
import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.repository.SessionJeuRepository;
import com.easybet.domain.repository.PortefeuilleRepository;
import java.math.BigDecimal;
import java.security.SecureRandom;

public class TerminerSessionUseCase {
    private final SessionJeuRepository sessionRepository;
    private final PortefeuilleRepository portefeuilleRepository;
    private final EffectuerDepotUseCase depotUseCase;

    // Générateur aléatoire sécurisé
    private final SecureRandom random = new SecureRandom();

    public TerminerSessionUseCase(SessionJeuRepository sessionRepository,
                                  PortefeuilleRepository portefeuilleRepository,
                                  EffectuerDepotUseCase depotUseCase) {
        this.sessionRepository = sessionRepository;
        this.portefeuilleRepository = portefeuilleRepository;
        this.depotUseCase = depotUseCase;
    }

    public SessionJeu execute(String sessionId) {
        SessionJeu session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session introuvable"));

        if ("TERMINEE".equals(session.getStatut())) {
            throw new RuntimeException("Session déjà terminée");
        }

        // --- LOGIQUE ALÉATOIRE (RNG) ---
        // 50% de chance de gagner le double, 50% de perdre
        boolean gagne = random.nextBoolean();

        BigDecimal gain;
        if (gagne) {
            gain = session.getMise().multiply(new BigDecimal("2")); // Gain = Mise * 2
        } else {
            gain = BigDecimal.ZERO; // Perdu
        }
        // -------------------------------

        // Créditer les gains si nécessaire
        if (gain.compareTo(BigDecimal.ZERO) > 0) {
            Long joueurIdLong = Long.valueOf(session.getJoueurId());
            Portefeuille portefeuille = portefeuilleRepository.findByJoueurId(joueurIdLong)
                    .orElseThrow(() -> new RuntimeException("Portefeuille introuvable"));

            depotUseCase.execute(portefeuille.getId(), gain, "GAIN_JEU", "Gain session " + sessionId);
        }

        session.terminer(gain);
        return sessionRepository.save(session);
    }
}