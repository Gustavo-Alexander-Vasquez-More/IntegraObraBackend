package com.integraobra.integraApi.DTO.categories;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CategoryRequestDTO {
    @NotEmpty(message = "El nombre de la categoría no puede estar vacío")
    private String name;
}
