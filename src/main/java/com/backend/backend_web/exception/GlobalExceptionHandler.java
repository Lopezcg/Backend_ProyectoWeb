package com.backend.backend_web.exception;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.cglib.core.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(RegistroNoEncontradoException.class)
        public ResponseEntity<ErrorMessage> handleRegistroNoEncontradoExceptioEntity(RegistroNoEncontradoException ex,
                        WebRequest webRequest) {

                ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(),
                                webRequest.getDescription(false), "NOT FOUND", HttpStatus.NOT_FOUND.value());

                return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ErrorMessage> handleUnauthorizedException(UnauthorizedException ex,
                        WebRequest webRequest) {
                ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(),
                                webRequest.getDescription(false), "UNAUTHORIZED", HttpStatus.UNAUTHORIZED.value());

                return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ErrorMessage> handleCustomIllegalArgumentException(IllegalArgumentException ex,
                        WebRequest webRequest) {
                ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(),
                                webRequest.getDescription(false), "BAD REQUEST", HttpStatus.BAD_REQUEST.value());

                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(
                        HttpMessageNotReadableException ex,
                        WebRequest request) {
                String message = "Error de parseo JSON: " + ex.getMostSpecificCause().getMessage();
                ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(),
                                request.getDescription(false), "NOT READABLE", HttpStatus.BAD_REQUEST.value());

                Map<String, Object> errorAttributes = new HashMap<>();
                errorAttributes.put("timestamp", errorMessage.getTimestamp());
                errorAttributes.put("message", errorMessage.getMessage());
                errorAttributes.put("path", errorMessage.getPath());
                errorAttributes.put("errorCode", errorMessage.getErrorCode());
                errorAttributes.put("status", errorMessage.getStatus());

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorAttributes);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                        WebRequest webRequest) {
                ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(),
                                webRequest.getDescription(false), "DATA VIOLATION", HttpStatus.BAD_REQUEST.value());

                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<ErrorMessage> handleIllegalStateException(IllegalStateException ex,
                        WebRequest webRequest) {
                ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(),
                                webRequest.getDescription(false), "ILLEGAL STATE", HttpStatus.BAD_REQUEST.value());

                return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorMessage> handleException(Exception ex, WebRequest webRequest) {
                ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(),
                                webRequest.getDescription(false), "INTERNAL SERVER ERROR",
                                HttpStatus.INTERNAL_SERVER_ERROR.value());

                return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
