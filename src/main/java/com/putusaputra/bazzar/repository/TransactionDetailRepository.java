package com.putusaputra.bazzar.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.putusaputra.bazzar.model.TransactionDetail;

public interface TransactionDetailRepository extends CrudRepository<TransactionDetail, String> {
    List<TransactionDetail> findAllByTransactionId(String transactionId);
}
