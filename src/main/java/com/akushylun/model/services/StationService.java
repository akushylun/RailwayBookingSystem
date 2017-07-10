package com.akushylun.model.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.StationDao;
import com.akushylun.model.dao.exceptions.ServiceException;
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

    public Optional<Station> getById(int stationId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    return stationDao.find(stationId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Station> getByAll() throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    return stationDao.findAll();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void createStation(Station station) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    stationDao.create(station);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void updateStation(Station station) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    stationDao.update(station);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void deleteStation(int stationId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    StationDao stationDao = daoFactory.createStationDao();
	    connection.begin();
	    stationDao.delete(stationId);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }
}
