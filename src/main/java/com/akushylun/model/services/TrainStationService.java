package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.TrainStationDao;
import com.akushylun.model.entities.TrainStation;

public class TrainStationService {

    private DaoFactory daoFactory;

    public TrainStationService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final TrainStationService INSTANCE = new TrainStationService(DaoFactory.getInstance());
    }

    public static TrainStationService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<TrainStation> getById(int trainStationId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainStationDao trainStationDao = daoFactory.createTrainStationDao();
	    connection.begin();
	    return trainStationDao.find(trainStationId);
	}
    }

    public List<TrainStation> getByAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainStationDao trainStationDao = daoFactory.createTrainStationDao();
	    connection.begin();
	    return trainStationDao.findAll();
	}
    }

    public void createTrainStation(TrainStation trainStation) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainStationDao trainStationDao = daoFactory.createTrainStationDao();
	    connection.begin();
	    trainStationDao.create(trainStation);
	    connection.commit();
	    connection.close();
	}
    }

    public void updateTrainStation(TrainStation trainStation) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainStationDao trainStationDao = daoFactory.createTrainStationDao();
	    connection.begin();
	    trainStationDao.update(trainStation);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteTrainStation(int trainStationId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainStationDao trainStationDao = daoFactory.createTrainStationDao();
	    connection.begin();
	    trainStationDao.delete(trainStationId);
	    connection.commit();
	    connection.close();
	}
    }
}
