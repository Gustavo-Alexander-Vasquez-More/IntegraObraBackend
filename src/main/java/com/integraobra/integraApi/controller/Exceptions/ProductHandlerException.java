package com.integraobra.integraApi.controller.Exceptions;

import com.integraobra.integraApi.DTO.ErrorResponseDTO;
import com.integraobra.integraApi.Exceptions.ProductExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductHandlerException {

    @ExceptionHandler(ProductExistException.class)
    public ErrorResponseDTO handleProductExistException(ProductExistException e){
        return new ErrorResponseDTO(409, e.getMessage(), null);
    }
}
