package com.putusaputra.bazzar.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.putusaputra.bazzar.model.Transaction;
import com.putusaputra.bazzar.model.TransactionDetail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionSaveRequest {
    @NotNull
    private Transaction transaction;
    @NotEmpty
    private List<TransactionDetail> transactionDetail;
}
