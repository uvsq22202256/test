package com.easybet.usecase;

import com.easybet.domain.repository.PortefeuilleRepository;
import org.springframework.stereotype.Service;

/**
 * Use Case - Supprimer un portefeuille
 */
@Service
public class SupprimerPortefeuilleUseCase {

    private final PortefeuilleRepository portefeuilleRepository;

    public SupprimerPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository) {
        this.portefeuilleRepository = portefeuilleRepository;
    }

    public void execute(Long portefeuilleId) {
        if (!portefeuilleRepository.findById(portefeuilleId).isPresent()) {
            throw new RuntimeException("Portefeuille non trouvé avec l'ID : " + portefeuilleId);
        }
        portefeuilleRepository.deleteById(portefeuilleId);
    }
}

