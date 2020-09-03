package com.putusaputra.bazzar.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageRequirement {
    private String id;
    private String name;
    private List<ProductRequirement> products;
}
