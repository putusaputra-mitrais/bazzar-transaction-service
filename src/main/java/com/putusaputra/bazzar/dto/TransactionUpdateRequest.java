package com.putusaputra.bazzar.dto;

import javax.validation.constraints.NotNull;

import com.putusaputra.bazzar.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionUpdateRequest {
    @NotNull
    private Transaction transaction;
}
