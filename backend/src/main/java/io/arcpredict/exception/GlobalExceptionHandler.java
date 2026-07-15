package io.arcpredict.exception;

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
        Exception.class
    )
    public ResponseEntity<ApiError>
    handleException(

        Exception exception

    ) {

        ApiError error =

            ApiError.builder()

                .error(
                    "Internal Server Error"
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
                HttpStatus.INTERNAL_SERVER_ERROR
            )

            .body(
                error
            );

    }

}