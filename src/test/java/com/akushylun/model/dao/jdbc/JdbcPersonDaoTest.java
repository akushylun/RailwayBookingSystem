package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

import com.akushylun.model.dao.PersonDao;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;

public class JdbcPersonDaoTest {
    static Connection connection;
    static PersonDao personDao;
    static Login LOGIN = new Login.Builder().withId(1).withLogin("mark123").withPassword("mark1990").build();
    static Person personInH2Script = new Person.Builder().withId(1).withName("mark").withSurname("johnson")
	    .withEmail("mark@gmail.com").withPersonLogin(LOGIN).withRole(Role.USER.name()).build();
    static Person person = new Person.Builder().withId(2).withName("John").withSurname("Doe")
	    .withEmail("john@gmail.com").withPersonLogin(LOGIN).withRole(Role.USER.name()).build();

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
	personDao = new JdbcPersonDao(connection, true);
	personDao.create(person);
    }

    @Test
    public void findByIdTest() {
	Person actualUser = personDao.find(personInH2Script.getId()).get();
	assertNotNull(actualUser.getId());
	assertNotNull(actualUser.getLogin().getId());
	assertEquals(personInH2Script.getName(), actualUser.getName());
	assertEquals(personInH2Script.getSurname(), actualUser.getSurname());
	assertEquals(personInH2Script.getEmail(), actualUser.getEmail());
	assertEquals(personInH2Script.getLogin().getLogin(), actualUser.getLogin().getLogin());
	assertEquals(personInH2Script.getLogin().getPassword(), actualUser.getLogin().getPassword());
	assertEquals(personInH2Script.getRole(), actualUser.getRole());
    }

    @Test
    public void findByEmailTest() {
	Person actualUser = personDao.findByEmail(personInH2Script.getEmail()).get();
	assertNotNull(actualUser.getId());
	assertNotNull(actualUser.getLogin().getId());
	assertEquals(personInH2Script.getName(), actualUser.getName());
	assertEquals(personInH2Script.getSurname(), actualUser.getSurname());
	assertEquals(personInH2Script.getEmail(), actualUser.getEmail());
	assertEquals(personInH2Script.getLogin().getLogin(), actualUser.getLogin().getLogin());
	assertEquals(personInH2Script.getLogin().getPassword(), actualUser.getLogin().getPassword());
	assertEquals(personInH2Script.getRole(), actualUser.getRole());
    }

    @Test
    public void findAllTest() {
	assertEquals(2, personDao.findAll().size());
    }

    @Test
    public void createUserTest() {
	assertNotNull(person.getId());
    }

    @Test
    public void updateUserTest() {
	String newSurname = "Watson";
	String newEmail = "johnWatson@gmail.com";
	Person newPerson = new Person.Builder().withId(personInH2Script.getId()).withName(personInH2Script.getName())
		.withSurname(newSurname).withEmail(newEmail).withPersonLogin(personInH2Script.getLogin())
		.withRole(personInH2Script.getRole()).build();
	personDao.update(newPerson);
	Person updatedPerson = personDao.findByEmail(newPerson.getEmail()).get();
	assertEquals(newPerson, updatedPerson);
	personDao.update(personInH2Script);
	assertEquals(personInH2Script, personDao.findByEmail(personInH2Script.getEmail()).get());
    }

    @Test
    public void deleteUserTest() {
	Person newPerson = new Person.Builder().withName("Sam").withSurname("Goldrat").withEmail("sam@yahoo.com")
		.withPersonLogin(LOGIN).withRole(Role.USER.name()).build();
	personDao.create(newPerson);
	assertNotNull(newPerson.getId());
	personDao.delete(newPerson.getId());
	assertEquals(2, personDao.findAll().size());
    }

    @AfterClass
    public static void closeConnection() throws Exception {
	connection.close();
    }
}
