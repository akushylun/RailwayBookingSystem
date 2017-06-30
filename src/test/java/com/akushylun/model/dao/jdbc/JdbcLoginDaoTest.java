package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.entity.Login;

public class JdbcLoginDaoTest {

    static Connection connection;
    static LoginDao loginDao;
    static Login newLogin = new Login.Builder().withId(2).withLogin("login1").withPassword("pass1").build();

    @BeforeClass
    public static void setH2DataBase()
	    throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
	Class.forName("org.h2.Driver").newInstance();
	connection = DriverManager.getConnection("jdbc:h2:mem:;MODE=mysql;", "sa", "");
	try {
	    File script = new File(JdbcPersonDaoTest.class.getResource("/mydb.sql").getFile());
	    RunScript.execute(connection, new FileReader(script));
	} catch (FileNotFoundException e) {
	    throw new RuntimeException("could not initialize with script");
	}
	loginDao = new JdbcLoginDao(connection);
	loginDao.create(newLogin);
    }

    @Test
    public void getByIdTest() {
	assertEquals(newLogin, loginDao.find(newLogin.getId()).get());
    }

    @Test
    public void getAllTest() {
	assertEquals(2, loginDao.findAll().size());
    }

    @Test
    public void createTest() {
	assertEquals(newLogin, loginDao.find(newLogin.getId()).get());
    }

    @Test
    public void deleteTest() {
	loginDao.delete(newLogin.getId());
	assertFalse(loginDao.find(2).isPresent());
    }

    @Test
    public void updateTest() {
	Login login = loginDao.find(newLogin.getId()).get();
	Login updateLogin = new Login.Builder().withId(login.getId()).withLogin("loginUpdate")
		.withPassword("passUpdate").build();
	loginDao.update(updateLogin);
	assertEquals(updateLogin, loginDao.find(updateLogin.getId()).get());
	loginDao.update(login);
	assertEquals(login, loginDao.find(login.getId()).get());
    }

    @AfterClass
    public static void closeConnection() throws Exception {
	connection.close();
    }
}
