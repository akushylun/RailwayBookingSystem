package com.akushylun.model.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.TrainDao;
import com.akushylun.model.entities.Train;
import com.akushylun.model.exceptions.ServiceException;

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

    public Optional<Train> getById(int trainId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    return trainDao.find(trainId);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Train> getByAll() throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    return trainDao.findAll();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public List<Train> getByAll(String stationStart, String stationEnd, String startDate) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    return trainDao.findAll(stationStart, stationEnd, startDate);
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void createTrain(Train train) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    trainDao.create(train);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void updateTrain(Train train) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    trainDao.update(train);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }

    public void deleteTrain(int trainId) throws ServiceException {
	try (DaoConnection connection = daoFactory.getConnection()) {
	    TrainDao trainDao = daoFactory.createTrainDao();
	    connection.begin();
	    trainDao.delete(trainId);
	    connection.commit();
	    connection.close();
	} catch (SQLException ex) {
	    throw new ServiceException(ex);
	}
    }
}
