package com.akushylun.model.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.dao.PersonDao;
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Person;

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

    public Optional<Person> getById(int userId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    return personDao.find(userId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Person> getAll() throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    return personDao.findAll();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void create(Person person) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    loginDao.create(person.getLogin());
	    personDao.create(person);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void update(Person person) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    personDao.update(person);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void deleteById(int id) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao personDao = daoFactory.createPersonDao();
	    connection.begin();
	    personDao.delete(id);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public Optional<Person> login(String loginName, String password) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    PersonDao loginDao = daoFactory.createPersonDao();
	    connection.begin();
	    return loginDao.findByLogin(loginName).filter(login -> password.equals(login.getLogin().getPassword()));
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }
}
