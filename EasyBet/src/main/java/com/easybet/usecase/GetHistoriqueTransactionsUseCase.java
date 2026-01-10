package com.easybet.usecase;

import com.easybet.domain.entity.Transaction;
import com.easybet.domain.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Use Case - Récupérer l'historique des transactions
 */
@Service
public class GetHistoriqueTransactionsUseCase {

    private final TransactionRepository transactionRepository;

    public GetHistoriqueTransactionsUseCase(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> execute(Long portefeuilleId) {
        return transactionRepository.findByPortefeuilleIdOrderByDateTransactionDesc(portefeuilleId);
    }

    public List<Transaction> executeByType(Long portefeuilleId, String typeTransaction) {
        return transactionRepository.findByPortefeuilleIdAndTypeTransaction(portefeuilleId, typeTransaction);
    }
}

