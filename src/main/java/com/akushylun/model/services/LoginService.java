package com.akushylun.model.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.entities.Login;
import com.akushylun.model.exceptions.ServiceException;

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

    public Optional<Login> getById(int loginId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    return loginDao.find(loginId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Login> getByAll() throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    return loginDao.findAll();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void createLogin(Login login) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    loginDao.create(login);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void updateLogin(Login login) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    loginDao.update(login);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void deleteLogin(int loginId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    LoginDao loginDao = daoFactory.createLoginDao();
	    connection.begin();
	    loginDao.delete(loginId);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }
}
