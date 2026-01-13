package com.easybet.usecase;

import com.easybet.domain.entity.Jeu;
import com.easybet.domain.repository.JeuRepository;
import com.easybet.infrastructure.event.JeuEventProducer;

import java.math.BigDecimal;

public class ModifierJeuUseCase {
    private final JeuRepository jeuRepository;
    private final JeuEventProducer eventProducer;

    public ModifierJeuUseCase(JeuRepository jeuRepository, JeuEventProducer eventProducer) {
        this.jeuRepository = jeuRepository;
        this.eventProducer = eventProducer;
    }

    public Jeu execute(String id, String nouveauNom, String nouveauType, BigDecimal nouveauRtp) {
        Jeu jeu = jeuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jeu introuvable : " + id));

        String ancienNom = jeu.getNom();

        // Mise à jour de l'entité
        jeu.mettreAJour(nouveauNom, nouveauType, nouveauRtp);

        // Sauvegarde
        Jeu jeuMisAJour = jeuRepository.save(jeu);

        // Envoi de l'événement
        eventProducer.publishJeuModifie(id, ancienNom, nouveauNom, nouveauRtp);

        return jeuMisAJour;
    }
}