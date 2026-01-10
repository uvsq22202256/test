package com.easybet.domain.repository;

import com.easybet.domain.entity.Transaction;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface Repository pour Transaction
 * Domain Layer - Définit le contrat pour accès aux données
 */
public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(Long id);

    List<Transaction> findByPortefeuilleId(Long portefeuilleId);

    List<Transaction> findByPortefeuilleIdAndTypeTransaction(Long portefeuilleId, String typeTransaction);

    List<Transaction> findByPortefeuilleIdOrderByDateTransactionDesc(Long portefeuilleId);

    List<Transaction> findByPortefeuilleIdAndDateTransactionBetween(
        Long portefeuilleId,
        LocalDateTime startDate,
        LocalDateTime endDate
    );

    void deleteById(Long id);
}

