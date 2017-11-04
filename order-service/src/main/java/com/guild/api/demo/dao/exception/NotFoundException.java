package com.guild.api.demo.dao.exception;

public class NotFoundException extends DaoException{
    public NotFoundException(Throwable cause) {
        super("Resource Not Found", cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
