package com.akushylun.model.dao.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.dao.PersonDao;
import com.akushylun.model.dao.SheduleDao;
import com.akushylun.model.dao.StationDao;
import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.dao.TrainDao;

public class JdbcDaoFactory extends DaoFactory {

    private static final String DB_URL = "url";
    private Connection connection;

    public JdbcDaoFactory() {
	try {
	    InputStream inputStream = DaoFactory.class.getResourceAsStream(DB_FILE);
	    Properties dbProps = new Properties();
	    dbProps.load(inputStream);
	    String url = dbProps.getProperty(DB_URL);
	    connection = DriverManager.getConnection(url, dbProps);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public DaoConnection getConnection() {
	return new JdbcDaoConnection(connection);
    }

    @Override
    public PersonDao createPersonDao() {
	return new JdbcPersonDao(connection);
    }

    @Override
    public LoginDao createLoginDao() {
	return new JdbcLoginDao(connection);
    }

    @Override
    public BookingDao createBookingDao() {
	return new JdbcBookingDao(connection);
    }

    @Override
    public SheduleDao createSheduleDao() {
	return new JdbcSheduleDao(connection);
    }

    @Override
    public StationDao createStationDao() {
	return new JdbcStationDao(connection);
    }

    @Override
    public TicketDao createTicketDao() {
	return new JdbcTicketDao(connection);
    }

    @Override
    public TrainDao createTrainDao() {
	return new JdbcTrainDao(connection);
    }

}
