package com.akushylun.model.dao.jdbc;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.dao.DaoConnection;
import com.akushylun.model.dao.DaoFactory;
import com.akushylun.model.dao.DepartureDao;
import com.akushylun.model.dao.LoginDao;
import com.akushylun.model.dao.PersonDao;
import com.akushylun.model.dao.StationDao;
import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.dao.TrainDao;
import com.akushylun.model.dao.TrainStationDao;

public class JdbcDaoFactory extends DaoFactory {

    private static final String DATASOURCE_NAME = "jdbc/railwaybooking";
    private static DataSource dataSource;

    static {
	try {
	    InitialContext initContext = new InitialContext();
	    Context envContext = (Context) initContext.lookup("java:comp/env");
	    dataSource = (DataSource) envContext.lookup(DATASOURCE_NAME);
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
	    return new JdbcPersonDao(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public LoginDao createLoginDao() {
	try {
	    return new JdbcLoginDao(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public BookingDao createBookingDao() {
	try {
	    return new JdbcBookingDao(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public DepartureDao createDepartureDao() {
	try {
	    return new JdbcDepartureDao(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public StationDao createStationDao() {
	try {
	    return new JdbcStationDao(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public TicketDao createTicketDao() {
	try {
	    return new JdbcTicketDao(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public TrainDao createTrainDao() {
	try {
	    return new JdbcTrainDao(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public TrainStationDao createTrainStationDao() {
	try {
	    return new JdbcTrainStationDao(dataSource.getConnection());
	} catch (SQLException e) {
	    throw new RuntimeException(e);
	}
    }

}
