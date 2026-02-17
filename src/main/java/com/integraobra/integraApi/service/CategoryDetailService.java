package com.integraobra.integraApi.service;

import com.integraobra.integraApi.DTO.categoryDetails.CategoryDetailRequestDTO;
import com.integraobra.integraApi.Exceptions.CategoryDetailExistException;
import com.integraobra.integraApi.model.Category;
import com.integraobra.integraApi.model.CategoryDetail;
import com.integraobra.integraApi.model.Product;
import com.integraobra.integraApi.repository.CategoryDetailRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryDetailService {

    public final CategoryDetailRepository categoryDetailRepository;
    public final CategoryService categoryService;
    public final ProductService productService;

    public CategoryDetailService(CategoryDetailRepository categoryDetailRepository, CategoryService categoryService, ProductService productService) {
        this.categoryDetailRepository = categoryDetailRepository;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    //Servicio para crear un detalle de categoría
    public void createCategoryDetail(CategoryDetailRequestDTO categoryDetailRequestDTO) {
        //Revisar si el detalle de categoría ya existe para el id de categoría y producto
        if (categoryDetailRepository.existsByCategoryIdAndProductId(categoryDetailRequestDTO.getCategoryId(), categoryDetailRequestDTO.getProductId())) {
            throw new CategoryDetailExistException("El detalle de categoría para la categoría con ID '" + categoryDetailRequestDTO.getCategoryId() + "' y el producto con ID '" + categoryDetailRequestDTO.getProductId() + "' ya existe.");
        }
        //Si no existe, lo creamos
        //Obtenemos el producto y la categoria por id
        Category category =categoryService.getCategoryById( categoryDetailRequestDTO.getCategoryId());
        Product product = productService.getProductById(categoryDetailRequestDTO.getProductId());

        categoryDetailRepository.save(new CategoryDetail(category, product));
    }

    //Servicio para eliminar un detalle de categoría por su ID
    public void deleteCategoryDetail(Long id) {
        //Verificamos si el detalle de categoría existe
        if (!categoryDetailRepository.existsById(id)) {
            throw new CategoryDetailExistException("El detalle de categoría con ID '" + id + "' no existe.");
        }
        //Si existe, lo eliminamos
        categoryDetailRepository.deleteById(id);
    }
}
