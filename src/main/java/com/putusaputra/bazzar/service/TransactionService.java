package com.putusaputra.bazzar.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.putusaputra.bazzar.dto.Product;
import com.putusaputra.bazzar.dto.ResponseWrapper;
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
            this.decreaseStock(details);
            
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
    
    public Product getProductById(String productId) {
        ResponseWrapper responseWrapper = null;
        Product result = null;
        ObjectMapper objMapper = new ObjectMapper();
        
        final String uri = String.format("http://localhost:8081/api/product/get-by-id?productId=%s", productId);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ResponseWrapper> response = restTemplate.getForEntity(uri, ResponseWrapper.class);
        responseWrapper = response.getBody();
        result = objMapper.convertValue(responseWrapper.getData(), Product.class);
        return result;
    }
    
    public void updateProduct(Product product) {
        final String uri = "http://localhost:8081/api/product/save";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, product, Product.class);
    }
    
    public void decreaseStock(List<TransactionDetail> details) {
        details.stream().forEach(detail -> {
            Product product = this.getProductById(detail.getProductId());
            product.setStock(product.getStock() - detail.getQty());
            this.updateProduct(product);
        });
    }
}
