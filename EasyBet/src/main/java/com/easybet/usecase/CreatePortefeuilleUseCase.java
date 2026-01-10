package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.repository.PortefeuilleRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * Use Case - Créer un portefeuille pour un nouveau joueur
 */
@Service
public class CreatePortefeuilleUseCase {

    private final PortefeuilleRepository portefeuilleRepository;

    public CreatePortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository) {
        this.portefeuilleRepository = portefeuilleRepository;
    }

    public Portefeuille execute(Long joueurId, String pseudo) {
        // Vérifier que le portefeuille n'existe pas déjà
        if (portefeuilleRepository.existsByJoueurId(joueurId)) {
            throw new RuntimeException("Un portefeuille existe déjà pour ce joueur : " + joueurId);
        }

        // Créer le portefeuille avec un solde initial de 0
        return portefeuilleRepository.createPortefeuilleForNewJoueur(joueurId, pseudo, BigDecimal.ZERO);
    }

    public Portefeuille executeWithInitialBalance(Long joueurId, String pseudo, BigDecimal soldeInitial) {
        // Vérifier que le portefeuille n'existe pas déjà
        if (portefeuilleRepository.existsByJoueurId(joueurId)) {
            throw new RuntimeException("Un portefeuille existe déjà pour ce joueur : " + joueurId);
        }

        // Vérifier le solde initial
        if (soldeInitial.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Le solde initial ne peut pas être négatif");
        }

        // Créer le portefeuille avec le solde initial spécifié
        return portefeuilleRepository.createPortefeuilleForNewJoueur(joueurId, pseudo, soldeInitial);
    }
}

