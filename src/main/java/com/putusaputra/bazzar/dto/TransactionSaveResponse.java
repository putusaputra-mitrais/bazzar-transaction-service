package com.putusaputra.bazzar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionSaveResponse {
    private String transactionId;
}
