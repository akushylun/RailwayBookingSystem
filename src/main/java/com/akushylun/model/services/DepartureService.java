package com.akushylun.model.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.DepartureDao;
import com.akushylun.model.dao.exceptions.ServiceException;
import com.akushylun.model.entities.Departure;

public class DepartureService {
    private DaoFactory daoFactory;

    public DepartureService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final DepartureService INSTANCE = new DepartureService(DaoFactory.getInstance());
    }

    public static DepartureService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<Departure> getById(int sheduleId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    return departureDao.find(sheduleId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Departure> getAll() throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    return departureDao.findAll();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void createShedule(Departure departure) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    departureDao.create(departure);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void updateShedule(Departure departure) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    departureDao.update(departure);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void deleteShedule(int sheduleId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    departureDao.delete(sheduleId);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }
}
