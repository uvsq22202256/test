package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.entity.SessionJeu;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.domain.repository.SessionJeuRepository;
import com.easybet.infrastructure.event.JeuEventProducer;
import java.math.BigDecimal;
import java.security.SecureRandom;

public class TerminerSessionUseCase {
    private final SessionJeuRepository sessionRepository;
    private final PortefeuilleRepository portefeuilleRepository;
    private final EffectuerDepotUseCase depotUseCase;
    private final JeuEventProducer eventProducer; // Ajout
    private final SecureRandom random = new SecureRandom();

    public TerminerSessionUseCase(SessionJeuRepository sessionRepository,
                                  PortefeuilleRepository portefeuilleRepository,
                                  EffectuerDepotUseCase depotUseCase,
                                  JeuEventProducer eventProducer) {
        this.sessionRepository = sessionRepository;
        this.portefeuilleRepository = portefeuilleRepository;
        this.depotUseCase = depotUseCase;
        this.eventProducer = eventProducer;
    }

    public SessionJeu execute(String sessionId) {
        SessionJeu session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session introuvable"));

        if ("TERMINEE".equals(session.getStatut())) {
            throw new RuntimeException("Session déjà terminée");
        }

        // Simulation simple du gain (50% de chance de doubler la mise)
        // Tu peux complexifier la logique ici si tu veux utiliser le RTP du jeu
        boolean gagne = random.nextBoolean();
        BigDecimal gain = gagne ? session.getMise().multiply(new BigDecimal("2")) : BigDecimal.ZERO;

        // Crédit du gain si gagnant
        if (gain.compareTo(BigDecimal.ZERO) > 0) {
            Long pId = Long.valueOf(session.getJoueurId());
            Portefeuille portefeuille = portefeuilleRepository.findByJoueurId(pId)
                    .orElseThrow(() -> new RuntimeException("Portefeuille introuvable"));

            depotUseCase.execute(portefeuille.getId(), gain, "GAIN_JEU", "Gain session " + sessionId);
        }

        session.terminer(gain);
        SessionJeu savedSession = sessionRepository.save(session);

        // Envoi event
        eventProducer.publishSessionTerminee(savedSession.getId(), savedSession.getJoueurId(), gain);

        return savedSession;
    }
}