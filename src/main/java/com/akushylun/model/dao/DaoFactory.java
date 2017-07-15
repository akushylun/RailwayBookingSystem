package com.akushylun.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
	    } catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
		throw new RuntimeException(e);
	    }
	}
	return instance;
    }

}
