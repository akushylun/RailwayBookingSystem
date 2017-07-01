package com.akushylun.model.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.SheduleDao;
import com.akushylun.model.entities.Shedule;

public class SheduleService {
    private DaoFactory daoFactory;

    public SheduleService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final SheduleService INSTANCE = new SheduleService(DaoFactory.getInstance());
    }

    public static SheduleService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<Shedule> getById(int sheduleId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    SheduleDao sheduleDao = daoFactory.createSheduleDao();
	    connection.begin();
	    return sheduleDao.find(sheduleId);
	}
    }

    public List<Shedule> getAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    SheduleDao sheduleDao = daoFactory.createSheduleDao();
	    connection.begin();
	    return sheduleDao.findAll();
	}
    }

    public List<Shedule> getAllByDate(LocalDateTime date) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    SheduleDao sheduleDao = daoFactory.createSheduleDao();
	    connection.begin();
	    return sheduleDao.findAllByDate(date);
	}
    }

    public void createShedule(Shedule shedule) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    SheduleDao sheduleDao = daoFactory.createSheduleDao();
	    connection.begin();
	    sheduleDao.create(shedule);
	    connection.commit();
	    connection.close();
	}
    }

    public void updateShedule(Shedule shedule) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    SheduleDao sheduleDao = daoFactory.createSheduleDao();
	    connection.begin();
	    sheduleDao.update(shedule);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteShedule(int sheduleId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    SheduleDao sheduleDao = daoFactory.createSheduleDao();
	    connection.begin();
	    sheduleDao.delete(sheduleId);
	    connection.commit();
	    connection.close();
	}
    }
}
