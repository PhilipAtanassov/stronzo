package com.example.stronzo.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

import static java.util.stream.Collectors.toMap;

// this is global
@RestControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto illegalArgumentException(MethodArgumentNotValidException e) {
        return new ErrorDto(
                HttpStatus.BAD_REQUEST.value(),
                "Sorry, that did not work",
                "validation-failed",
                e.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .collect(toMap(FieldError::getField, FieldError::getDefaultMessage)));
    }


    public static class ErrorDto {
        public ErrorDto(int httpStatus, String message, String code, Map<String, String> moreDetails) {
            this.httpStatus = httpStatus;
            this.message = message;
            this.code = code;
            this.moreDetails = moreDetails;
        }

        public int httpStatus;
        public String message;
        public String code;
        public Map<String, String> moreDetails;
    }
}
