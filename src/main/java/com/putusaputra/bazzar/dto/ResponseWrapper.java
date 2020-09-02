package com.putusaputra.bazzar.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseWrapper {
    private String message;
    private Object data;
    private int httpStatus;
    private List<String> errors;
}
