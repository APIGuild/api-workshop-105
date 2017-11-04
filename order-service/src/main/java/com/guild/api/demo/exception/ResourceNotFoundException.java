package com.guild.api.demo.exception;

public class ResourceNotFoundException extends RuntimeException {
    private final String code = "N01";
    private final String title = "Resource Not Found";

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }
}
