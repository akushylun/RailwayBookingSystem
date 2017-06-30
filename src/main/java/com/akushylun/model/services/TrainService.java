package com.akushylun.model.services;

import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.TrainDao;
import com.akushylun.model.entities.Train;

public class TrainService {
    private DaoFactory daoFactory;

    public TrainService(DaoFactory daoFactory) {
	this.daoFactory = daoFactory;
    }

    private static class Holder {
	static final TrainService INSTANCE = new TrainService(DaoFactory.getInstance());
    }

    public static TrainService getInstance() {
	return Holder.INSTANCE;
    }

    public Optional<Train> getById(int trainId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    return trainDao.find(trainId);
	}
    }

    public List<Train> getByAll() {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    return trainDao.findAll();
	}
    }

    public void createTrain(Train train) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    trainDao.create(train);
	    connection.commit();
	    connection.close();
	}
    }

    public void updateTrain(Train train) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    trainDao.update(train);
	    connection.commit();
	    connection.close();
	}
    }

    public void deleteTrain(int trainId) {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    trainDao.delete(trainId);
	    connection.commit();
	    connection.close();
	}
    }
}
