package com.integraobra.integraApi.controller.Exceptions;

import com.integraobra.integraApi.DTO.ErrorResponseDTO;
import com.integraobra.integraApi.Exceptions.ErrorCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AuthHandlerException {

    @ExceptionHandler(ErrorCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> handleErrorCredentialsException(ErrorCredentialsException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(401, ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}
