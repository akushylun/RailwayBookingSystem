package com.akushylun.model.dao.exceptions;

import java.sql.SQLException;

/**
 * Custom Exception class for handling SQLException
 */
public class DaoException extends SQLException {

    private static final long serialVersionUID = 1L;

    public DaoException() {
    }

    public DaoException(String message, Throwable exception) {
	super(message, exception);
    }

    public DaoException(String message) {
	super(message);
    }

    public DaoException(Throwable exception) {
	super(exception);
    }
}
