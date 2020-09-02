package com.putusaputra.bazzar.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putusaputra.bazzar.dto.TransactionSaveRequest;
import com.putusaputra.bazzar.dto.TransactionSaveResponse;
import com.putusaputra.bazzar.dto.TransactionUpdateRequest;
import com.putusaputra.bazzar.dto.TransactionUpdateResponse;
import com.putusaputra.bazzar.model.Transaction;
import com.putusaputra.bazzar.model.TransactionDetail;
import com.putusaputra.bazzar.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository repository;
    
    public List<Transaction> findAll() {
        return (List<Transaction>) this.repository.findAll();
    }
    
    public Transaction findById(String transId) {
        return this.repository.findById(transId).orElse(null);
    }
    
    @Transactional
    public TransactionSaveResponse save(TransactionSaveRequest request) {
        try {
            Transaction transaction = request.getTransaction();
            List<TransactionDetail> details = request.getTransactionDetail();
            details = details.stream()
                    .map(detail -> {
                        detail.setTransaction(transaction);
                        return detail;
                    }).collect(Collectors.toList());
            transaction.setTransactionDetail(details);
            Transaction savedTransaction = this.repository.save(transaction);
            return TransactionSaveResponse.builder()
                    .transactionId(savedTransaction.getId())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Transactional
    public TransactionUpdateResponse update(TransactionUpdateRequest request) {
        try {
            Transaction findTrans = this.findById(request.getTransaction().getId());
            Transaction transaction = request.getTransaction();
            
            findTrans.setDate(transaction.getDate());
            findTrans.setUserId(transaction.getUserId());
            findTrans.setStatus(transaction.getStatus());
            findTrans.setGrandTotal(transaction.getGrandTotal());
            
            Transaction savedTransaction = this.repository.save(findTrans);
            return TransactionUpdateResponse.builder()
                    .transactionId(savedTransaction.getId())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    @Transactional
    public void deleteById(String transactionId) {
        try {
            this.repository.deleteById(transactionId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
