package com.abdulrehman1793.recipe.controllers;

import com.abdulrehman1793.recipe.exceptions.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormat(Exception exception) {

        log.error("Handling Number Format Exception");
        log.error(exception.getMessage());

        return exception.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public String handleBadRequest(Exception exception) {

        log.error("Handling BadRequest Exception");
        log.error(exception.getMessage());

        return exception.getMessage();
    }
}
