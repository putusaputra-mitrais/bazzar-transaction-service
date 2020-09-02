package com.putusaputra.bazzar.repository;

import org.springframework.data.repository.CrudRepository;

import com.putusaputra.bazzar.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

}
