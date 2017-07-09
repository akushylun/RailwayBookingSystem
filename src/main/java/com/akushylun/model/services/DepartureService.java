package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.DepartureDao;
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

    public Optional<Departure> getById(int sheduleId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    return departureDao.find(sheduleId);
	}
    }

    public List<Departure> getAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    return departureDao.findAll();
	}
    }

    public void createShedule(Departure departure) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    departureDao.create(departure);
	    connection.commit();
	    connection.close();
	}
    }

    public void updateShedule(Departure departure) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    departureDao.update(departure);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteShedule(int sheduleId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    DepartureDao departureDao = daoFactory.createDepartureDao();
	    connection.begin();
	    departureDao.delete(sheduleId);
	    connection.commit();
	    connection.close();
	}
    }
}
