package com.easybet.infrastructure.persistence.mapper;

import com.easybet.domain.entity.Promotion;
import com.easybet.infrastructure.persistence.entity.PromotionEntity;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {
    public PromotionEntity toEntity(Promotion domain) {
        if (domain == null) return null;
        PromotionEntity entity = new PromotionEntity();
        entity.setId(domain.getId());
        entity.setCode(domain.getCode());
        entity.setMontant(domain.getMontant());
        entity.setDateFin(domain.getDateFin());
        return entity;
    }

    public Promotion toDomain(PromotionEntity entity) {
        if (entity == null) return null;
        Promotion domain = new Promotion();
        domain.setId(entity.getId());
        domain.setCode(entity.getCode());
        domain.setMontant(entity.getMontant());
        domain.setDateFin(entity.getDateFin());
        return domain;
    }
}