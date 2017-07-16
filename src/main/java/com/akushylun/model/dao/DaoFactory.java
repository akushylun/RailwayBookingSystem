package com.akushylun.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.akushylun.controller.util.LogMessage;

public abstract class DaoFactory {

    public abstract DaoConnection getConnection();

    public abstract PersonDao createPersonDao();

    public abstract LoginDao createLoginDao();

    public abstract BookingDao createBookingDao();

    public abstract DepartureDao createDepartureDao();

    public abstract StationDao createStationDao();

    public abstract TicketDao createTicketDao();

    public abstract TrainDao createTrainDao();

    public abstract TrainStationDao createTrainStationDao();

    public static final String DB_FILE = "/db.properties";
    private static final Logger LOGGER = Logger.getLogger(DaoFactory.class);
    private static final String DB_FACTORY_CLASS = "factory.class";
    private static DaoFactory instance;

    public static DaoFactory getInstance() {
	if (instance == null) {
	    try {
		InputStream inputStream = DaoFactory.class.getResourceAsStream(DB_FILE);
		Properties dbProps = new Properties();
		dbProps.load(inputStream);
		String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
		instance = (DaoFactory) Class.forName(factoryClass).newInstance();
	    } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
		String errorMessage = LogMessage.DAO_FACTORY_CREATION_ERROR;
		LOGGER.error(errorMessage, ex);
		throw new RuntimeException(errorMessage, ex);
	    }
	}
	return instance;
    }

}
