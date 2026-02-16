package com.integraobra.integraApi.DTO.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

//Se usa para devolver mensajes de exito al cliente, como por ejemplo "Usuario creado correctamente" o "Contraseña actualizada correctamente"
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserMessageDTO {
    private int status;
    private String message;
    private LocalDateTime timestamp;
}
