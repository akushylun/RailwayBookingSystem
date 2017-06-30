package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.entity.Login;

public class LoginService {
    private DaoFactory daoFactory;

    public LoginService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final LoginService INSTANCE = new LoginService(DaoFactory.getInstance());
    }

    public static LoginService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<Login> getById(int loginId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    return loginDao.find(loginId);
	}
    }

    public List<Login> getByAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    return loginDao.findAll();
	}
    }

    public void createLogin(Login login) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    loginDao.create(login);
	    connection.commit();
	    connection.close();
	}
    }

    public void updateLogin(Login login) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    loginDao.update(login);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteLogin(int loginId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    loginDao.delete(loginId);
	    connection.commit();
	    connection.close();
	}
    }
}
