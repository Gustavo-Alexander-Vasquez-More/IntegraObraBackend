package com.integraobra.integraApi.controller.Exceptions;

import com.integraobra.integraApi.DTO.ErrorResponseDTO;
import com.integraobra.integraApi.Exceptions.CategoryDetailExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CategoryDetailHandlerException {

    @ExceptionHandler( CategoryDetailExistException.class)
    public ErrorResponseDTO handleCategoryDetailExistException(CategoryDetailExistException e){
        return new ErrorResponseDTO(409, e.getMessage(), null);
    }
}
