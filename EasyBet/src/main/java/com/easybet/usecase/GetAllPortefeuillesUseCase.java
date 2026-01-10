package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.repository.PortefeuilleRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Use Case - Récupérer tous les portefeuilles
 */
@Service
public class GetAllPortefeuillesUseCase {

    private final PortefeuilleRepository portefeuilleRepository;

    public GetAllPortefeuillesUseCase(PortefeuilleRepository portefeuilleRepository) {
        this.portefeuilleRepository = portefeuilleRepository;
    }

    public List<Portefeuille> execute() {
        return portefeuilleRepository.findAll();
    }
}

