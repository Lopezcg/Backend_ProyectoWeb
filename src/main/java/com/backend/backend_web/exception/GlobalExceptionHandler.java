package com.backend.backend_web.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RegistroNoEncontradoException.class)
    public ResponseEntity<ErrorMessage> handleRegistroNoEncontradoExceptioEntity(RegistroNoEncontradoException ex,
            WebRequest webRequest) {

        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(),
                webRequest.getDescription(false), "NOT FOUND");

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
