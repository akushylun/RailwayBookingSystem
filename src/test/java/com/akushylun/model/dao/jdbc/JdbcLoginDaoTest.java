package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.entities.Login;

public class JdbcLoginDaoTest {

    private Connection connection;
    private DatabaseConfig databaseConfig = new DatabaseConfig(connection);
    private LoginDao dao;

    @Before
    public void setH2DataBase() {
	databaseConfig.establishConnection();
	databaseConfig.runDatabaseScripts();
	dao = new JdbcLoginDao(databaseConfig.getConnection());
    }

    @Test
    public void getByIdTest() throws SQLException {
	Login actualLogin = dao.find(1).get();
	assertNotNull(actualLogin);
	assertEquals(1, actualLogin.getId());
	assertEquals("mark123@gmail.com", actualLogin.getEmail());
	assertEquals("mark1990", actualLogin.getPassword());
    }

    @Test
    public void getAllTest() throws SQLException {
	assertEquals(2, dao.findAll().size());
    }

    @Test
    public void createTest() throws SQLException {
	Login expectedLogin = new Login.Builder().withEmail("liza").withPassword("lizz123").build();
	dao.create(expectedLogin);
	assertNotNull(expectedLogin.getId());
	assertEquals(expectedLogin, dao.find(expectedLogin.getId()).get());
    }

    @Test
    public void deleteTest() throws SQLException {
	Login expectedLogin = new Login.Builder().withEmail("liza").withPassword("lizz123").build();
	dao.create(expectedLogin);
	dao.delete(expectedLogin.getId());
	assertFalse(dao.find(expectedLogin.getId()).isPresent());
    }

    @Test
    public void updateTest() throws SQLException {
	Login expectedLogin = new Login.Builder().withId(1).withEmail("Mark_P@yahoo.com").withPassword("mark123Pass")
		.build();
	dao.update(expectedLogin);
	assertEquals(expectedLogin, dao.find(expectedLogin.getId()).get());
    }

    @After
    public void closeConnection() throws Exception {
	databaseConfig.closeConnection();
    }
}
