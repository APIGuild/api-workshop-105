package com.guild.api.demo.controller.error;

import static java.lang.String.valueOf;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

public final class ErrorBuilder {
    private ErrorBuilder() {
    }

    public static Error buildBadRequestError(String detail) {
        return buildBasicError(valueOf(BAD_REQUEST.value()), "S001", "Bad Request", detail);
    }

    public static Error buildServiceError(String detail) {
        return buildBasicError(valueOf(SERVICE_UNAVAILABLE.value()), "S005", "Service Error", detail);
    }

    public static Error buildBasicError(String status, String code, String title, String detail) {
        Error error = new Error();
        error.setStatus(status);
        error.setCode(code);
        error.setTitle(title);
        error.setDetail(detail);
        return error;
    }
}
