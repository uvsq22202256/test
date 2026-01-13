package com.easybet.usecase;

import com.easybet.domain.entity.Joueur;
import com.easybet.domain.repository.JoueurRepository;
import org.springframework.stereotype.Service;

/**
 * Use Case pour modifier les informations d'un joueur
 */
@Service
public class UpdateJoueurUseCase {

    private final JoueurRepository joueurRepository;

    public UpdateJoueurUseCase(JoueurRepository joueurRepository) {
        this.joueurRepository = joueurRepository;
    }

    /**
     * Modifie les informations d'un joueur existant
     * @param id ID du joueur à modifier
     * @param pseudo Nouveau pseudo (null pour ne pas modifier)
     * @param email Nouvel email (null pour ne pas modifier)
     * @param password Nouveau mot de passe (null pour ne pas modifier)
     * @param kycValide Nouveau statut KYC (null pour ne pas modifier)
     * @return Le joueur modifié
     */
    public Joueur execute(Long id, String pseudo, String email, String password, Boolean kycValide) {
        Joueur joueur = joueurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé avec l'ID: " + id));

        // Mise à jour des champs si fournis
        if (pseudo != null && !pseudo.isBlank()) {
            joueur.setPseudo(pseudo);
        }
        if (email != null && !email.isBlank()) {
            joueur.setEmail(email);
        }
        if (password != null && !password.isBlank()) {
            joueur.setPassword(password);
        }
        if (kycValide != null) {
            joueur.setKycValide(kycValide);
        }

        return joueurRepository.save(joueur);
    }
}

