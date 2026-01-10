package com.easybet.infrastructure.persistence.mapper;

import com.easybet.domain.entity.Portefeuille;
import com.easybet.infrastructure.persistence.entity.PortefeuilleEntity;

/**
 * Mapper - Convertir entre PortefeuilleEntity et Portefeuille (domain)
 */
public class PortefeuilleMapper {

    public static Portefeuille toDomain(PortefeuilleEntity entity) {
        if (entity == null) return null;

        Portefeuille portefeuille = new Portefeuille();
        portefeuille.setId(entity.getId());
        portefeuille.setJoueurId(entity.getJoueurId());
        portefeuille.setPseudo(entity.getPseudo());
        portefeuille.setSoldeReel(entity.getSoldeReel());
        portefeuille.setSoldeBonus(entity.getSoldeBonus());
        portefeuille.setDevise(entity.getDevise());
        portefeuille.setStatut(entity.getStatut());
        portefeuille.setDateCreation(entity.getDateCreation());
        portefeuille.setDateModification(entity.getDateModification());

        return portefeuille;
    }

    public static PortefeuilleEntity toEntity(Portefeuille portefeuille) {
        if (portefeuille == null) return null;

        PortefeuilleEntity entity = new PortefeuilleEntity();
        entity.setId(portefeuille.getId());
        entity.setJoueurId(portefeuille.getJoueurId());
        entity.setPseudo(portefeuille.getPseudo());
        entity.setSoldeReel(portefeuille.getSoldeReel());
        entity.setSoldeBonus(portefeuille.getSoldeBonus());
        entity.setDevise(portefeuille.getDevise());
        entity.setStatut(portefeuille.getStatut());
        entity.setDateCreation(portefeuille.getDateCreation());
        entity.setDateModification(portefeuille.getDateModification());

        return entity;
    }
}

