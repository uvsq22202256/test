package com.easybet.infrastructure.persistence.mapper;

import com.easybet.domain.entity.Transaction;
import com.easybet.infrastructure.persistence.entity.TransactionEntity;

/**
 * Mapper - Convertir entre TransactionEntity et Transaction (domain)
 */
public class TransactionMapper {

    public static Transaction toDomain(TransactionEntity entity) {
        if (entity == null) return null;

        Transaction transaction = new Transaction();
        transaction.setId(entity.getId());
        transaction.setPortefeuilleId(entity.getPortefeuilleId());
        transaction.setPortefeuilleDestinataireId(entity.getPortefeuilleDestinataireId());
        transaction.setTypeTransaction(entity.getTypeTransaction());
        transaction.setMontant(entity.getMontant());
        transaction.setAncienSolde(entity.getAncienSolde());
        transaction.setNouveauSolde(entity.getNouveauSolde());
        transaction.setStatut(entity.getStatut());
        transaction.setDateTransaction(entity.getDateTransaction());
        transaction.setReference(entity.getReference());
        transaction.setMethodePayement(entity.getMethodePayement());
        transaction.setDescription(entity.getDescription());

        return transaction;
    }

    public static TransactionEntity toEntity(Transaction transaction) {
        if (transaction == null) return null;

        TransactionEntity entity = new TransactionEntity();
        entity.setId(transaction.getId());
        entity.setPortefeuilleId(transaction.getPortefeuilleId());
        entity.setPortefeuilleDestinataireId(transaction.getPortefeuilleDestinataireId());
        entity.setTypeTransaction(transaction.getTypeTransaction());
        entity.setMontant(transaction.getMontant());
        entity.setAncienSolde(transaction.getAncienSolde());
        entity.setNouveauSolde(transaction.getNouveauSolde());
        entity.setStatut(transaction.getStatut());
        entity.setDateTransaction(transaction.getDateTransaction());
        entity.setReference(transaction.getReference());
        entity.setMethodePayement(transaction.getMethodePayement());
        entity.setDescription(transaction.getDescription());

        return entity;
    }
}

