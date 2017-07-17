package com.akushylun.model.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.akushylun.model.dao.PersonDao;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;

public class JdbcPersonDaoTest {

    private Connection connection;
    private DatabaseConfig databaseConfig = new DatabaseConfig(connection);
    private PersonDao personDao;

    @Before
    public void setUp() {
	databaseConfig.establishConnection();
	databaseConfig.runDatabaseScripts();
	personDao = new JdbcPersonDao(databaseConfig.getConnection());
    }

    @Test
    public void findByIdTest() {
	Person actualUser = personDao.find(1).get();
	assertNotNull(actualUser);
	assertEquals("mark", actualUser.getName());
	assertEquals("johnson", actualUser.getSurname());
	assertEquals("mark123@gmail.com", actualUser.getLogin().getEmail());
	assertEquals("mark1990", actualUser.getLogin().getPassword());
	assertEquals(Role.USER, actualUser.getRole());
    }

    @Test
    public void findAllTest() {
	assertEquals(2, personDao.findAll().size());
    }

    @Test
    public void createUserTest() {
	Person expectedPerson = new Person.Builder().withName("sindi").withSurname("abbot").withRole(Role.USER)
		.withPersonLogin(new Login.Builder().withId(1).build()).build();
	personDao.create(expectedPerson);
	assertNotNull(expectedPerson.getId());
	assertEquals(expectedPerson.getId(), personDao.find(expectedPerson.getId()).get().getId());
    }

    @Test
    public void updateUserTest() {
	Person person = new Person.Builder().withId(1).withName("jack").withSurname("johnson").withRole(Role.USER)
		.build();
	personDao.update(person);
	assertEquals(person.getName(), personDao.find(person.getId()).get().getName());
    }

    @After
    public void closeConnection() {
	databaseConfig.closeConnection();
    }
}
