package com.integraobra.integraApi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "products" )
@NoArgsConstructor
@Getter @Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cardImageUrl;
    private String sku;
    private int stock;
    private boolean available=true;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;
    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags;
    private BigDecimal salePrice;
    private BigDecimal rentPrice;
    private boolean isForRent=true;
    private boolean isForSale;
    private boolean priceVisibleForRent;
    private boolean priceVisibleForSale;
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Product(String name, String cardImageUrl, String sku, int stock, String description, List<String> tags, BigDecimal salePrice, BigDecimal rentPrice, boolean isForSale, boolean priceVisibleForRent, boolean priceVisibleForSale) {
        this.name = name;
        this.cardImageUrl = cardImageUrl;
        this.sku = sku;
        this.stock = stock;
        this.description = description;
        this.tags = tags;
        this.salePrice = salePrice;
        this.rentPrice = rentPrice;
        this.isForSale = isForSale;
        this.priceVisibleForRent = priceVisibleForRent;
        this.priceVisibleForSale = priceVisibleForSale;
    }

}
