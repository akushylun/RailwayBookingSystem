package com.akushylun.model.dao.jdbc;

import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

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

    private DataSource dataSource;

    public JdbcDaoFactory() {
	try {
	    InitialContext initContext = new InitialContext();
	    dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/railwaybooking");
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public DaoConnection getConnection() {
	try {
	    return new JdbcDaoConnection(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public PersonDao createPersonDao() {
	try {
	    return new JdbcPersonDao(dataSource.getConnection(), true);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public LoginDao createLoginDao() {
	try {
	    return new JdbcLoginDao(dataSource.getConnection(), true);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public BookingDao createBookingDao() {
	try {
	    return new JdbcBookingDao(dataSource.getConnection(), true);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public SheduleDao createSheduleDao() {
	try {
	    return new JdbcSheduleDao(dataSource.getConnection(), true);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public StationDao createStationDao() {
	try {
	    return new JdbcStationDao(dataSource.getConnection(), true);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public TicketDao createTicketDao() {
	try {
	    return new JdbcTicketDao(dataSource.getConnection(), true);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public TrainDao createTrainDao() {
	try {
	    return new JdbcTrainDao(dataSource.getConnection(), true);
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

}
