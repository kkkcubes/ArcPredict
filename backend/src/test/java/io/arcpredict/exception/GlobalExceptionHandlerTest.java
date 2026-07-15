package io.arcpredict.exception;

import org.junit.jupiter.api.Test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler =
        new GlobalExceptionHandler();

    @Test
    void shouldHandleValidationException() throws Exception {

        BeanPropertyBindingResult bindingResult =
            new BeanPropertyBindingResult(
                new Object(),
                "request"
            );

        bindingResult.addError(

            new FieldError(

                "request",

                "question",

                "Question is required"

            )

        );

        Method method =
            Object.class.getDeclaredMethods()[0];

        MethodArgumentNotValidException exception =

            new MethodArgumentNotValidException(

                null,

                bindingResult

            );

        ResponseEntity<Map<String, Object>> response =

            handler.handleValidation(
                exception
            );

        assertEquals(
            HttpStatus.BAD_REQUEST,
            response.getStatusCode()
        );

        assertNotNull(
            response.getBody()
        );

        assertEquals(
            "Validation Failed",
            response.getBody().get("error")
        );

        assertEquals(
            400,
            response.getBody().get("status")
        );

        Map<?, ?> validationErrors =

            (Map<?, ?>)
                response.getBody()
                    .get("validationErrors");

        assertEquals(
            "Question is required",
            validationErrors.get("question")
        );

        assertNotNull(
            response.getBody().get("timestamp")
        );

    }

    @Test
    void shouldHandleMethodNotSupportedException() {

        HttpRequestMethodNotSupportedException exception =

            new HttpRequestMethodNotSupportedException(
                "POST"
            );

        ResponseEntity<ApiError> response =

            handler.handleMethodNotSupported(
                exception
            );

        assertEquals(
            HttpStatus.METHOD_NOT_ALLOWED,
            response.getStatusCode()
        );

        assertNotNull(
            response.getBody()
        );

        assertEquals(
            "Method Not Allowed",
            response.getBody().getError()
        );

        assertTrue(

            response.getBody()
                .getMessage()
                .contains("POST")

        );

        assertTrue(

            response.getBody()
                .getTimestamp() > 0

        );

    }

    @Test
    void shouldHandleGenericException() {

        Exception exception =

            new Exception(
                "Unexpected failure"
            );

        ResponseEntity<ApiError> response =

            handler.handleException(
                exception
            );

        assertEquals(
            HttpStatus.INTERNAL_SERVER_ERROR,
            response.getStatusCode()
        );

        assertNotNull(
            response.getBody()
        );

        assertEquals(
            "Internal Server Error",
            response.getBody().getError()
        );

        assertEquals(
            "Unexpected failure",
            response.getBody().getMessage()
        );

        assertTrue(

            response.getBody()
                .getTimestamp() > 0

        );

    }

}