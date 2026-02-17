package com.integraobra.integraApi.controller.Exceptions;

import com.integraobra.integraApi.Exceptions.ClientExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClientHandlerException {

    @ExceptionHandler(ClientExistException.class)
    public String handleClientExistException(ClientExistException ex) {
        return ex.getMessage();
    }
}
