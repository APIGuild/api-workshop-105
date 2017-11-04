package com.guild.api.demo.exception;

import com.guild.api.demo.dao.exception.DaoException;

public class DaoRuntimeException extends RuntimeException {

    public DaoRuntimeException(DaoException daoException) {
        super(daoException);
    }

    public DaoException getDaoException() {
        return (DaoException) getCause();
    }

    @Override
    public String getMessage() {
        return String.format("%s. source = %s", super.getMessage(), getDaoException().getSource());
    }
}
