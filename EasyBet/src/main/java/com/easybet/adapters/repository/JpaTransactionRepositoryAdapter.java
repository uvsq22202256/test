package com.easybet.adapters.repository;

import com.easybet.domain.entity.Transaction;
import com.easybet.domain.repository.TransactionRepository;
import com.easybet.infrastructure.persistence.entity.TransactionEntity;
import com.easybet.infrastructure.persistence.mapper.TransactionMapper;
import com.easybet.adapters.repository.jpa.SpringDataTransactionRepository;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptateur Repository Transaction
 * Implémente l'interface domain avec Spring Data JPA
 */
@Component
public class JpaTransactionRepositoryAdapter implements TransactionRepository {

    private final SpringDataTransactionRepository springDataRepository;

    public JpaTransactionRepositoryAdapter(SpringDataTransactionRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = TransactionMapper.toEntity(transaction);
        TransactionEntity savedEntity = springDataRepository.save(entity);
        return TransactionMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return springDataRepository.findById(id)
                .map(TransactionMapper::toDomain);
    }

    @Override
    public List<Transaction> findByPortefeuilleId(Long portefeuilleId) {
        return springDataRepository.findByPortefeuilleId(portefeuilleId)
                .stream()
                .map(TransactionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByPortefeuilleIdAndTypeTransaction(Long portefeuilleId, String typeTransaction) {
        return springDataRepository.findByPortefeuilleIdAndTypeTransaction(portefeuilleId, typeTransaction)
                .stream()
                .map(TransactionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByPortefeuilleIdOrderByDateTransactionDesc(Long portefeuilleId) {
        return springDataRepository.findByPortefeuilleIdOrderByDateTransactionDesc(portefeuilleId)
                .stream()
                .map(TransactionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transaction> findByPortefeuilleIdAndDateTransactionBetween(
            Long portefeuilleId, LocalDateTime startDate, LocalDateTime endDate) {
        return springDataRepository.findByPortefeuilleIdAndDateTransactionBetween(portefeuilleId, startDate, endDate)
                .stream()
                .map(TransactionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataRepository.deleteById(id);
    }
}

