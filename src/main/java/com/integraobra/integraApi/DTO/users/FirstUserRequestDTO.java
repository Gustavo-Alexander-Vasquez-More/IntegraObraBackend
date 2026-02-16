package com.integraobra.integraApi.DTO.users;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FirstUserRequestDTO {
    @NotEmpty(message = "El nombre de usuario no puede estar vacío.")
    private String username;
    @NotEmpty(message = "La contraseña no puede estar vacía.")
    private String password;
}
