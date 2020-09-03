package com.putusaputra.bazzar.dto;

import com.putusaputra.bazzar.constant.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private String id;
    private String name;
    private int stock;
    private double purchasePrice;
    private double sellPrice;
    private ProductStatus status;
    private PackageRequirement packageRequirement;
}
