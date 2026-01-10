package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.repository.PortefeuilleRepository;
import org.springframework.stereotype.Service;

/**
 * Use Case - Récupérer un portefeuille par ID
 */
@Service
public class GetPortefeuilleUseCase {

    private final PortefeuilleRepository portefeuilleRepository;

    public GetPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository) {
        this.portefeuilleRepository = portefeuilleRepository;
    }

    public Portefeuille execute(Long portefeuilleId) {
        return portefeuilleRepository.findById(portefeuilleId)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé avec l'ID : " + portefeuilleId));
    }
}

