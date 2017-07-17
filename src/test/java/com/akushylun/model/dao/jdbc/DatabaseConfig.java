package com.akushylun.model.dao.jdbc;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.RunScript;

public class DatabaseConfig {

    private Connection connection;

    public DatabaseConfig(Connection connection) {
	this.connection = connection;
    }

    public Connection getConnection() {
	return connection;
    }

    /**
     * Register driver and establish connection to the given Database URL, using
     * user name and password
     */
    public void establishConnection() {
	try {
	    Class.forName("org.h2.Driver").newInstance();
	    connection = DriverManager.getConnection("jdbc:h2:mem:;MODE=mysql;", "sa", "");
	} catch (Exception e) {
	    throw new RuntimeException("could not get connection");
	}
    }

    /**
     * Run scripts, one for create database, second for insert data into
     * database
     */
    public void runDatabaseScripts() {
	try {
	    File scriptCreate = new File(DatabaseConfig.class.getResource("/create.sql").getFile());
	    File scriptInsert = new File(DatabaseConfig.class.getResource("/insert.sql").getFile());
	    RunScript.execute(connection, new FileReader(scriptCreate));
	    RunScript.execute(connection, new FileReader(scriptInsert));
	} catch (Exception e) {
	    throw new RuntimeException("could not initialize with script" + e.getMessage());
	}
    }

    public void closeConnection() {
	try {
	    connection.close();
	} catch (SQLException e) {
	    throw new RuntimeException("could not close connection");
	}
    }

}
