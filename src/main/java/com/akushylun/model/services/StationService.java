package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.StationDao;
import com.akushylun.model.entities.Station;

public class StationService {
    private DaoFactory daoFactory;

    public StationService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final StationService INSTANCE = new StationService(DaoFactory.getInstance());
    }

    public static StationService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<Station> getById(int stationId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    return stationDao.find(stationId);
	}
    }

    public List<Station> getByAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    return stationDao.findAll();
	}
    }

    public void createStation(Station station) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    stationDao.create(station);
	    connection.commit();
	    connection.close();
	}
    }

    public void updateStation(Station station) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    stationDao.update(station);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteStation(int stationId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    stationDao.delete(stationId);
	    connection.commit();
	    connection.close();
	}
    }
}
