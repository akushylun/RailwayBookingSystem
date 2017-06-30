package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.dao.PersonDao;
import com.akushylun.model.entity.Person;

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

    public Optional<Person> login(String email, String password) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    return personDao.findByEmail(email).filter(person -> password.equals(person.getLogin().getPassword()));
	}
    }

    public List<Person> getAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    return personDao.findAll();
	}
    }

    public void create(Person person) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    personDao.create(person);
	    loginDao.create(person.getLogin());
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
	    connection.begin();
	    personDao.delete(id);
	    connection.commit();
	    connection.close();
	}
    }

}
