package com.example.app_incidencias_backend_v2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = InternalServerException.class)
    public ResponseEntity<?> internalServerException(InternalServerException ex) {
        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .ok(false)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = SQLException.class)
    public ResponseEntity<?> sqlException(SQLException ex) {
        ApiExceptionDto exceptionDto = ApiExceptionDto.builder()
                .ok(false)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now().toString())
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
