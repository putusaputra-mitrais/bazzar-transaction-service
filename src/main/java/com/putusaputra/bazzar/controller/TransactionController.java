package com.putusaputra.bazzar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.putusaputra.bazzar.dto.ResponseWrapper;
import com.putusaputra.bazzar.dto.TransactionSaveRequest;
import com.putusaputra.bazzar.dto.TransactionSaveResponse;
import com.putusaputra.bazzar.dto.TransactionUpdateRequest;
import com.putusaputra.bazzar.dto.TransactionUpdateResponse;
import com.putusaputra.bazzar.model.Transaction;
import com.putusaputra.bazzar.service.TransactionService;
import com.putusaputra.bazzar.util.GlobalUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/transaction")
@Slf4j
public class TransactionController {
    private static final String TRANSACTION_DATA_MESSAGE = "Transaction Data";
    private static final String TRANSACTION_SAVE_SUCCESS_MESSAGE = "Transacton Data saved successfully";
    private static final String TRANSACTION_SAVE_FAILED_MESSAGE = "Transacton Data save failed";
    private static final String TRANSACTION_UPDATE_SUCCESS_MESSAGE = "Transacton Data updated successfully";
    private static final String TRANSACTION_UPDATE_FAILED_MESSAGE = "Transacton Data update failed";
    private static final String TRANSACTION_DELETE_SUCCESS_MESSAGE = "Transacton Data deleted successfully";
    private static final String TRANSACTION_DELETE_FAILED_MESSAGE = "Transacton Data delete failed";
    
    @Autowired
    private TransactionService service;
    
    @GetMapping("/get-all")
    public ResponseEntity<ResponseWrapper> getAll() {
        List<Transaction> response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        response = this.service.findAll();
        responseMessage = GlobalUtil.createResponse(TRANSACTION_DATA_MESSAGE, response, errors);
        
        return ResponseEntity.ok(responseMessage);
    }
    
    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseWrapper> getById(@RequestParam("transactionId") String transactionId) {
        Transaction response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        response = this.service.findById(transactionId);
        responseMessage = GlobalUtil.createResponse(TRANSACTION_DATA_MESSAGE, response, errors);
        
        return ResponseEntity.ok(responseMessage);
    }
    
    @PostMapping("/save")
    public ResponseEntity<ResponseWrapper> save(@Valid @RequestBody TransactionSaveRequest request, BindingResult bindingResult) {
        TransactionSaveResponse response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(bindError -> errors.add(bindError.getDefaultMessage()));
            return ResponseEntity.ok(GlobalUtil.createResponse(TRANSACTION_SAVE_FAILED_MESSAGE, response, errors));
        }
        
        try {
            response = this.service.save(request);
            responseMessage = GlobalUtil.createResponse(TRANSACTION_SAVE_SUCCESS_MESSAGE, response, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = GlobalUtil.createResponse(TRANSACTION_SAVE_FAILED_MESSAGE, response, errors);
            log.error(null, e);
        }

        return ResponseEntity.ok(responseMessage);
    }
    
    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper> update(@Valid @RequestBody TransactionUpdateRequest request, BindingResult bindingResult) {
        TransactionUpdateResponse response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(bindError -> errors.add(bindError.getDefaultMessage()));
            return ResponseEntity.ok(GlobalUtil.createResponse(TRANSACTION_UPDATE_FAILED_MESSAGE, response, errors));
        }
        
        try {
            response = this.service.update(request);
            responseMessage = GlobalUtil.createResponse(TRANSACTION_UPDATE_SUCCESS_MESSAGE, response, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = GlobalUtil.createResponse(TRANSACTION_UPDATE_FAILED_MESSAGE, response, errors);
            log.error(null, e);
        }

        return ResponseEntity.ok(responseMessage);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseWrapper> delete(@RequestParam("transactionId") String transactionId) {
        Transaction response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();

        try {
            this.service.deleteById(transactionId);
            responseMessage = GlobalUtil.createResponse(TRANSACTION_DELETE_SUCCESS_MESSAGE, response, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = GlobalUtil.createResponse(TRANSACTION_DELETE_FAILED_MESSAGE, response, errors);
            log.error(null, e);
        }

        return ResponseEntity.ok(responseMessage);
    }
}
