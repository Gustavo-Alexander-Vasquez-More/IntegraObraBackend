package com.integraobra.integraApi.controller.Exceptions;

import com.integraobra.integraApi.DTO.ErrorResponseDTO;
import com.integraobra.integraApi.Exceptions.CategoryExistException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CategoryHandlerException {

    @ExceptionHandler(CategoryExistException.class)
    public ErrorResponseDTO handleCategoryExistException(CategoryExistException e){
        return new ErrorResponseDTO(409, e.getMessage(), null);
    }

}
