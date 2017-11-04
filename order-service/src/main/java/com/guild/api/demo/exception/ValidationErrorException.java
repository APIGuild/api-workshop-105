package com.guild.api.demo.exception;

import com.guild.api.demo.controller.error.Errors;

public class ValidationErrorException extends RuntimeException {
    private Errors errors;

    public ValidationErrorException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
