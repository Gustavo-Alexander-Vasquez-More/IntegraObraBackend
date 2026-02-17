package com.integraobra.integraApi.service;

import com.integraobra.integraApi.DTO.categories.CategoryResponseDTO;
import com.integraobra.integraApi.Exceptions.CategoryExistException;
import com.integraobra.integraApi.model.Category;
import com.integraobra.integraApi.repository.CategoryDetailRepository;
import com.integraobra.integraApi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    public final CategoryRepository categoryRepository;
    public final CategoryDetailRepository categoryDetailRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryDetailRepository categoryDetailRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryDetailRepository = categoryDetailRepository;
    }

    //Servicio para crear un a nueva categoría
    public CategoryResponseDTO createCategory(String name){
        //Verificamos si la categoría ya existe
        if(categoryRepository.existsByName(name)){
            throw new CategoryExistException("La categoría con nombre '" + name + "' ya existe.");
        }
        //Si no existe, la creamos
        Category category = new Category(name);
        categoryRepository.save(category);
        return new CategoryResponseDTO(category.getId(), category.getName());
    }
    
    //Servicio para eliminar una categoría por su ID, eliminando también los categoryDetails asociados en cascada
    public void deleteCategory(Long id){
        //Verificamos si la categoría existe
        if(!categoryRepository.existsById(id)){
            throw new CategoryExistException("La categoría con ID '" + id + "' no existe.");
        }
        //Si existe, eliminamos los categoryDetails asociados en cascada y luego la categoría
        categoryDetailRepository.deleteByCategoryId(id);
        categoryRepository.deleteById(id);
    }

    //Servicio para editar el nombre de una categoría por su ID
    public CategoryResponseDTO editCategory(Long id, String newName) {
        //Verificamos si la categoría existe
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryExistException("La categoría con ID '" + id + "' no existe."));
        //Verificamos si el nuevo nombre ya existe en otra categoría
        if (categoryRepository.existsByName(newName)) {
            throw new CategoryExistException("La categoría con nombre '" + newName + "' ya existe.");
        }
        //Si el nuevo nombre es válido, actualizamos el nombre de la categoría
        category.setName(newName);
        categoryRepository.save(category);
        return new CategoryResponseDTO(category.getId(), category.getName());
    }

    //Servicio para obtener todas las categorías en una lista de CategoryResponseDTO
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(category -> new CategoryResponseDTO(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    //Servicio para obtener una categoría por su ID
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryExistException("La categoría con ID '" + id + "' no existe."));
    }

}
