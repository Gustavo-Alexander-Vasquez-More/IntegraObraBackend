package com.integraobra.integraApi.DTO.categoryDetails;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CategoryDetailRequestDTO {
    @NotNull(message = "El ID de la categoría no puede estar vacío")
    private Long categoryId;
    @NotNull(message = "El ID del producto no puede estar vacío")
    private Long productId;
}
