package com.cheapestcart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheapestItemResponse {
    private Long productId;
    private String productName;
    private String category;
    
    private Long storeId;
    private String chainName;
    private String storeName;
    
    private BigDecimal price;
    private Integer quantity;
    private String unit;
    private BigDecimal pricePerUnit;
}
