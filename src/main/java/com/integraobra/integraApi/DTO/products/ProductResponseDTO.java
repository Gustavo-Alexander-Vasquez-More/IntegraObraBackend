package com.integraobra.integraApi.DTO.products;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductResponseDTO {
    private Long id;
    private String name;
    private String cardImageUrl;
    private String sku;
    private Integer stock;
    private String description;
    private List<String> tags;
    private BigDecimal salePrice;
    private BigDecimal rentPrice;
    private Boolean isForSale;
    private Boolean isForRent;
    private Boolean priceVisibleForRent;
    private Boolean priceVisibleForSale;
}
