package com.backend.backend_web.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("title", "Request incorrecta");
        body.put("detail", "Error de análisis JSON: tipo incorrecto de los campos");

        Throwable cause = ex.getRootCause();
        if (cause instanceof InvalidFormatException) {
            InvalidFormatException ife = (InvalidFormatException) cause;
            body.put("additional detail",
                    "Field error in '" + ife.getPathReference() + "' - expected type '"
                            + ife.getTargetType().getSimpleName() + "', but got a value that could not be converted: '"
                            + ife.getValue() + "'.");
        }

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
