package com.integraobra.integraApi.service;

import com.integraobra.integraApi.DTO.products.ProductRequestDTO;
import com.integraobra.integraApi.DTO.products.ProductResponseDTO;
import com.integraobra.integraApi.Exceptions.ProductExistException;
import com.integraobra.integraApi.model.Product;
import com.integraobra.integraApi.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Servicio para verificar si el sku ya existe en la base de datos
    public boolean existsBySku(String sku) {
        return productRepository.existsBySku(sku.trim());
    }

    //Servicio para verificar si el nombre ya existe en la base de datos
    public boolean existsByName(String name) {
        return productRepository.existsByName(name.trim());
    }

    //Servicio para crear un producto verificando que el SKU y el nombre no existan previamente
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        if (existsBySku(productRequestDTO.getSku())) {
            throw new ProductExistException("El SKU ya existe en la base de datos, por favor elige otro");
        }
        if (existsByName(productRequestDTO.getName())) {
            throw new ProductExistException("El nombre del producto ya existe en la base de datos, por favor elige otro");
        }

        Product product=new Product(
                productRequestDTO.getName().trim(),
                productRequestDTO.getCardImageUrl().trim(),
                productRequestDTO.getSku().trim(),
                productRequestDTO.getStock(),
                productRequestDTO.getDescription().trim(),
                productRequestDTO.getTags(),
                productRequestDTO.getSalePrice(),
                productRequestDTO.getRentPrice(),
                productRequestDTO.getIsForSale(),
                productRequestDTO.getPriceVisibleForRent(),
                productRequestDTO.getPriceVisibleForSale()
        );
        productRepository.save(product);

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getCardImageUrl(),
                product.getSku(),
                product.getStock(),
                product.getDescription(),
                product.getTags(),
                product.getSalePrice(),
                product.getRentPrice(),
                product.isForSale(),
                product.isForRent(),
                product.isPriceVisibleForRent(),
                product.isPriceVisibleForSale()
        );
    }


}
