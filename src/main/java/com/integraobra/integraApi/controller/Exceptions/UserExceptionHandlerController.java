package com.integraobra.integraApi.controller.Exceptions;

import com.integraobra.integraApi.DTO.ErrorResponseDTO;
import com.integraobra.integraApi.Exceptions.UserExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class UserExceptionHandlerController {

    // Maneja la excepción UserExistException y devuelve una respuesta adecuada
    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserExistsException(UserExistException ex) {
        // Creamos el DTO con la información del error
        ErrorResponseDTO error = new ErrorResponseDTO(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                LocalDateTime.now()
        );

        // Retornamos el DTO dentro del ResponseEntity con el status 409
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

}
