package com.backend.backend_web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RegistroNoEncontradoException extends RuntimeException {
    public RegistroNoEncontradoException(String message) {
        super(message);
    }
}