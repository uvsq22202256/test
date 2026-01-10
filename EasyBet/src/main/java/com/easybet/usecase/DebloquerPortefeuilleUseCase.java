package com.easybet.usecase;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.domain.repository.PortefeuilleRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Use Case - Débloquer un portefeuille
 */
@Service
public class DebloquerPortefeuilleUseCase {

    private final PortefeuilleRepository portefeuilleRepository;

    public DebloquerPortefeuilleUseCase(PortefeuilleRepository portefeuilleRepository) {
        this.portefeuilleRepository = portefeuilleRepository;
    }

    public Portefeuille execute(Long portefeuilleId) {
        Portefeuille portefeuille = portefeuilleRepository.findById(portefeuilleId)
                .orElseThrow(() -> new RuntimeException("Portefeuille non trouvé avec l'ID : " + portefeuilleId));

        if (!"BLOQUE".equals(portefeuille.getStatut())) {
            throw new RuntimeException("Le portefeuille n'est pas bloqué");
        }

        portefeuille.setStatut("ACTIF");
        portefeuille.setDateModification(LocalDateTime.now());
        return portefeuilleRepository.save(portefeuille);
    }
}

