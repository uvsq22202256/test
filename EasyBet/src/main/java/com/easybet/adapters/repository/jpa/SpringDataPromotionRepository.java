package com.easybet.repository.jpa;

import com.easybet.infrastructure.persistence.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataPromotionRepository extends JpaRepository<PromotionEntity, Long> {
    Optional<PromotionEntity> findByCode(String code);
}