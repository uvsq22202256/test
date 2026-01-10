package com.easybet.adapters.repository.jpa;

import com.easybet.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Spring Data JPA Repository pour Transaction
 */
@Repository
public interface SpringDataTransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findByPortefeuilleId(Long portefeuilleId);

    List<TransactionEntity> findByPortefeuilleIdAndTypeTransaction(Long portefeuilleId, String typeTransaction);

    List<TransactionEntity> findByPortefeuilleIdOrderByDateTransactionDesc(Long portefeuilleId);

    Page<TransactionEntity> findByPortefeuilleIdOrderByDateTransactionDesc(Long portefeuilleId, Pageable pageable);

    List<TransactionEntity> findByPortefeuilleIdAndDateTransactionBetween(
        Long portefeuilleId,
        LocalDateTime startDate,
        LocalDateTime endDate
    );
}

