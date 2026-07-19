package io.arcpredict.exception;

import jakarta.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
        LoggerFactory.getLogger(
            GlobalExceptionHandler.class
        );

    @ExceptionHandler(
        MethodArgumentNotValidException.class
    )
    public ResponseEntity<Map<String, Object>>
    handleValidation(

        MethodArgumentNotValidException exception

    ) {

        Map<String, String> errors =
            new HashMap<>();

        exception
            .getBindingResult()
            .getFieldErrors()
            .forEach(

                error ->

                    errors.put(

                        error.getField(),

                        error.getDefaultMessage()

                    )

            );

        Map<String, Object> response =
            new HashMap<>();

        response.put(
            "error",
            "Validation Failed"
        );

        response.put(
            "status",
            400
        );

        response.put(
            "validationErrors",
            errors
        );

        response.put(
            "timestamp",
            System.currentTimeMillis()
        );

        return ResponseEntity
            .badRequest()
            .body(response);

    }

    @ExceptionHandler(
        HttpRequestMethodNotSupportedException.class
    )
    public ResponseEntity<ApiError>
    handleMethodNotSupported(

        HttpRequestMethodNotSupportedException exception

    ) {

        ApiError error =

            ApiError.builder()

                .error(
                    "Method Not Allowed"
                )

                .message(
                    exception.getMessage()
                )

                .timestamp(
                    System.currentTimeMillis()
                )

                .build();

        return ResponseEntity

            .status(
                HttpStatus.METHOD_NOT_ALLOWED
            )

            .body(
                error
            );

    }

    @ExceptionHandler(
        NoSuchElementException.class
    )
    public ResponseEntity<ApiError>
    handleNotFound(

        NoSuchElementException exception

    ) {

        ApiError error =

            ApiError.builder()

                .error(
                    "Resource Not Found"
                )

                .message(
                    exception.getMessage()
                )

                .timestamp(
                    System.currentTimeMillis()
                )

                .build();

        return ResponseEntity

            .status(
                HttpStatus.NOT_FOUND
            )

            .body(
                error
            );

    }

    @ExceptionHandler(
        IllegalArgumentException.class
    )
    public ResponseEntity<ApiError>
    handleIllegalArgument(

        IllegalArgumentException exception

    ) {

        ApiError error =

            ApiError.builder()

                .error(
                    "Bad Request"
                )

                .message(
                    exception.getMessage()
                )

                .timestamp(
                    System.currentTimeMillis()
                )

                .build();

        return ResponseEntity

            .status(
                HttpStatus.BAD_REQUEST
            )

            .body(
                error
            );

    }

    @ExceptionHandler(
        ConstraintViolationException.class
    )
    public ResponseEntity<ApiError>
    handleConstraintViolation(

        ConstraintViolationException exception

    ) {

        ApiError error =

            ApiError.builder()

                .error(
                    "Validation Failed"
                )

                .message(
                    exception.getMessage()
                )

                .timestamp(
                    System.currentTimeMillis()
                )

                .build();

        return ResponseEntity

            .badRequest()

            .body(
                error
            );

    }

    @ExceptionHandler(
        Exception.class
    )
    public ResponseEntity<ApiError>
    handleException(

        Exception exception

    ) {

        log.error(
            "Unhandled exception",
            exception
        );

        ApiError error =

            ApiError.builder()

                .error(
                    "Internal Server Error"
                )

                .message(
                    "An unexpected error occurred."
                )

                .timestamp(
                    System.currentTimeMillis()
                )

                .build();

        return ResponseEntity

            .status(
                HttpStatus.INTERNAL_SERVER_ERROR
            )

            .body(
                error
            );

    }

}