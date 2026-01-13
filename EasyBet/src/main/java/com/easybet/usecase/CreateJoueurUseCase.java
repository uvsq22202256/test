package com.easybet.usecase;

import com.easybet.domain.entity.Joueur;
import com.easybet.domain.repository.JoueurRepository;
import com.easybet.infrastructure.event.JoueurEventProducer;
import org.springframework.stereotype.Service;

@Service
public class CreateJoueurUseCase {

    private final JoueurRepository repository;
    private final JoueurEventProducer eventProducer;
    private final CreatePortefeuilleUseCase createPortefeuilleUseCase;

    public CreateJoueurUseCase(JoueurRepository repository, JoueurEventProducer eventProducer,
                             CreatePortefeuilleUseCase createPortefeuilleUseCase) {
        this.repository = repository;
        this.eventProducer = eventProducer;
        this.createPortefeuilleUseCase = createPortefeuilleUseCase;
    }

    public Joueur execute(String pseudo, String email, String password) {
        if (pseudo == null || pseudo.trim().isEmpty()) {
            throw new IllegalArgumentException("Pseudo obligatoire");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email obligatoire");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Mot de passe obligatoire");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email invalide");
        }

        if (repository.findByPseudo(pseudo).isPresent()) {
            throw new IllegalArgumentException("Pseudo deja existant");
        }

        Joueur joueur = Joueur.builder()
                .pseudo(pseudo.trim())
                .email(email.trim())
                .password(password.trim())
                .soldeReel(0.0)
                .soldeBonus(0.0)
                .kycValide(false)
                .build();

        Joueur savedJoueur = repository.save(joueur);

        // Créer automatiquement le portefeuille du joueur
        createPortefeuilleUseCase.execute(savedJoueur.getId(), savedJoueur.getPseudo());

        // Publier l'événement de création
        eventProducer.publishJoueurCreeEvent(savedJoueur.getId(), savedJoueur.getPseudo(), savedJoueur.getEmail());

        return savedJoueur;
    }
}