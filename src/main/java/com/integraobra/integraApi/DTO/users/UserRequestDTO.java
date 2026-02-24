package com.integraobra.integraApi.DTO.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserRequestDTO {
    @NotEmpty(message = "El nombre de usuario no puede estar vacío." )
    private String username;
    @NotEmpty(message = "La contraseña no puede estar vacía." )
    private String password;
    @Pattern(regexp = "ADMIN|EMPLOYEE",
            message = "Los roles permitidos son ADMIN o EMPLOYEE.")
    @NotEmpty(message = "El rol de usuario no puede estar vacío." )
    private String role;
}
