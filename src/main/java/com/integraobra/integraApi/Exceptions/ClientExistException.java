package com.integraobra.integraApi.Exceptions;

public class ClientExistException extends RuntimeException {
    public ClientExistException(String message) {
        super(message);
    }
}
