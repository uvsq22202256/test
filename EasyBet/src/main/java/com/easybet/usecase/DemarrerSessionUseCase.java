package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.entity.SessionJeu;
import com.easybet.domain.repository.JeuRepository;
import com.easybet.domain.repository.PortefeuilleRepository;
import com.easybet.domain.repository.SessionJeuRepository;
import com.easybet.infrastructure.event.JeuEventProducer;
import java.math.BigDecimal;
import java.util.UUID;

public class DemarrerSessionUseCase {
    private final SessionJeuRepository sessionRepository;
    private final JeuRepository jeuRepository;
    private final PortefeuilleRepository portefeuilleRepository;
    private final EffectuerRetraitUseCase retraitUseCase;
    private final JeuEventProducer eventProducer; // Ajout

    public DemarrerSessionUseCase(SessionJeuRepository sessionRepository,
                                  JeuRepository jeuRepository,
                                  PortefeuilleRepository portefeuilleRepository,
                                  EffectuerRetraitUseCase retraitUseCase,
                                  JeuEventProducer eventProducer) {
        this.sessionRepository = sessionRepository;
        this.jeuRepository = jeuRepository;
        this.portefeuilleRepository = portefeuilleRepository;
        this.retraitUseCase = retraitUseCase;
        this.eventProducer = eventProducer;
    }

    public SessionJeu execute(String joueurId, String jeuId, BigDecimal mise) {
        if (!jeuRepository.existsById(jeuId)) {
            throw new RuntimeException("Jeu introuvable : " + jeuId);
        }

        // On vérifie le portefeuille (on suppose joueurId == portefeuilleId ou on le cherche)
        // Note: Dans ton projet actuel, tu utilises souvent joueurIdLong. Adapte si nécessaire.
        Long pId = Long.valueOf(joueurId);
        Portefeuille portefeuille = portefeuilleRepository.findByJoueurId(pId)
                .orElseThrow(() -> new RuntimeException("Portefeuille introuvable pour le joueur " + joueurId));

        // Débit de la mise
        retraitUseCase.execute(portefeuille.getId(), mise, "MISE_JEU", "Mise jeu " + jeuId);

        // Création session
        SessionJeu session = SessionJeu.creer(UUID.randomUUID().toString(), joueurId, jeuId, mise);
        SessionJeu savedSession = sessionRepository.save(session);

        // Envoi event
        eventProducer.publishSessionDemarree(savedSession.getId(), joueurId, jeuId, mise);

        return savedSession;
    }
}