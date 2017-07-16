package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.akushylun.controller.util.LogMessage;
import com.akushylun.model.dao.DaoConnection;

public class JdbcDaoConnection implements DaoConnection {

    final static Logger LOGGER = Logger.getLogger(JdbcDaoConnection.class);

    private Connection connection;
    private boolean inTransaction = false;

    public JdbcDaoConnection(Connection connection) {
	this.connection = connection;
    }

    @Override
    public void begin() {
	try {
	    connection.setAutoCommit(false);
	    inTransaction = true;
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.BEGIN_TRANSACTION_ERROR;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void commit() {
	try {
	    connection.commit();
	    inTransaction = false;
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.COMMIT_TRANSACTION_ERROR;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void rollback() {
	try {
	    connection.rollback();
	    inTransaction = false;
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.ROLLBACK_TRANSACTION_ERROR;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void close() {
	if (inTransaction) {
	    rollback();
	}
	try {
	    connection.close();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.CLOSE_CONNECTION_ERROR;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}

    }

    Connection getConnection() {
	return connection;
    }

}
