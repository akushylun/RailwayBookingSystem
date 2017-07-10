package com.akushylun.model.dao.exceptions;

import java.sql.SQLException;

/**
 * Custom Exception class for handling SQLException
 */
public class ServiceException extends SQLException {

    private static final long serialVersionUID = 1L;

    public ServiceException() {
    }

    public ServiceException(String message, Throwable exception) {
	super(message, exception);
    }

    public ServiceException(String message) {
	super(message);
    }

    public ServiceException(Throwable exception) {
	super(exception);
    }
}
