package com.easybet.infrastructure.persistence.mapper;

import com.easybet.domain.entity.Joueur;
import com.easybet.infrastructure.persistence.entity.JoueurEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper entre entité métier et entité JPA
 * Infrastructure Layer
 */
@Component
public class JoueurMapper {

    /**
     * Convertit une entité JPA vers une entité métier
     */
    public Joueur toDomain(JoueurEntity entity) {
        if (entity == null) {
            return null;
        }

        return Joueur.builder()
                .id(entity.getId())
                .pseudo(entity.getPseudo())
                .email(entity.getEmail())
                .soldeReel(entity.getSoldeReel())
                .soldeBonus(entity.getSoldeBonus())
                .kycValide(entity.isKycValide())
                .build();
    }

    /**
     * Convertit une entité métier vers une entité JPA
     */
    public JoueurEntity toEntity(Joueur domain) {
        if (domain == null) {
            return null;
        }

        return JoueurEntity.builder()
                .id(domain.getId())
                .pseudo(domain.getPseudo())
                .email(domain.getEmail())
                .soldeReel(domain.getSoldeReel())
                .soldeBonus(domain.getSoldeBonus())
                .kycValide(domain.isKycValide())
                .build();
    }
}

