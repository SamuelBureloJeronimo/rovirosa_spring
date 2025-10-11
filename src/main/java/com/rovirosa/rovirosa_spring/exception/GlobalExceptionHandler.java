package com.rovirosa.rovirosa_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.rovirosa.rovirosa_spring.DTOs.ApiError;

import org.springframework.web.context.request.WebRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // ⚠️ Error general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor",
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ⚠️ Error por datos inválidos (por ejemplo validaciones fallidas)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        String messages = ex.getBindingResult().getAllErrors()
                .stream().map(err -> err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Datos inválidos",
                messages,
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ⚠️ Error al acceder a la base de datos
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiError> handleDatabaseExceptions(DataAccessException ex, WebRequest request) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error en base de datos",
                ex.getMostSpecificCause().getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ⚠️ Error de tipo incorrecto en parámetros (ej: pasar texto donde espera número)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
        String message = String.format("El parámetro '%s' debe ser del tipo '%s'",
                ex.getName(), ex.getRequiredType().getSimpleName());
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Tipo de parámetro inválido",
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // ⚠️ Error si se excede el tamaño permitido de archivos
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiError> handleMaxSizeException(MaxUploadSizeExceededException ex, WebRequest request) {
        ApiError error = new ApiError(
                HttpStatus.PAYLOAD_TOO_LARGE.value(),
                "Archivo demasiado grande",
                "El tamaño máximo permitido ha sido excedido",
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.PAYLOAD_TOO_LARGE);
    }
}