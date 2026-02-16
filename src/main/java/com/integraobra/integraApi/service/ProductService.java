package com.integraobra.integraApi.service;

import com.integraobra.integraApi.model.Product;
import com.integraobra.integraApi.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //Servicio para obtener un producto por su ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El producto con ID '" + id + "' no existe."));
    }

}
