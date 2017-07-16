package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.dao.PersonDao;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;

public class PersonService {

    private DaoFactory daoFactory;

    public PersonService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final PersonService INSTANCE = new PersonService(DaoFactory.getInstance());
    }

    public static PersonService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<Person> getById(int userId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    return personDao.find(userId);
	}
    }

    public List<Person> getAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    return personDao.findAll();
	}
    }

    public List<Person> getAll(Role role) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    return personDao.findAll(role);
	}
    }

    public void create(Person person) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    loginDao.create(person.getLogin());
	    personDao.create(person);
	    connection.commit();
	    connection.close();
	}
    }

    public void update(Person person) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    personDao.update(person);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteById(int id) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    personDao.delete(id);
	    loginDao.delete(id);
	    connection.commit();
	    connection.close();
	}
    }

    public Optional<Person> login(String loginName, String password) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao loginDao = daoFactory.createPersonDao();
	    connection.begin();
	    return loginDao.findByLogin(loginName).filter(login -> password.equals(login.getLogin().getPassword()));
	}
    }
}
