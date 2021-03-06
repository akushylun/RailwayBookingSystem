package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.akushylun.controller.util.LogMessage;
import com.akushylun.model.dao.DepartureDao;
import com.akushylun.model.entities.Departure;
import com.akushylun.model.entities.Train;

public class JdbcDepartureDao implements DepartureDao {

    private static final String SELECT_DEPARTURE_BY_ID = "SELECT d.d_id, d.d_datetime, t.tr_id, t.tr_name FROM departure as d "
	    + "INNER JOIN train as t ON t.tr_id = d.d_train_tr_id WHERE d.d_id = ?";
    private static final String SELECT_ALL_DEPARTURES = "SELECT d.d_id, d.d_datetime, t.tr_id, t.tr_name FROM departure as d "
	    + "INNER JOIN train as t ON t.tr_id = d.d_train_tr_id";
    private static final String CREATE_DEPARTURE = "INSERT INTO departure (d_datetime, d_train_tr_id) "
	    + " VALUES (?,?)";
    private static final String UPDATE_DEPARTURE = "UPDATE departure SET d_datetime = ?, d_train_tr_id = ? "
	    + " WHERE d_id = ?";
    private static final String DELETE_DEPARTURE_BY_ID = "DELETE FROM departure WHERE d_id = ?";

    private static final Logger LOGGER = Logger.getLogger(JdbcDepartureDao.class);
    private Connection connection;

    public JdbcDepartureDao(Connection connection) {
	this.connection = connection;
    }

    @Override
    public Optional<Departure> find(int id) {
	Optional<Departure> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_DEPARTURE_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Departure departure;
	    if (rs.next()) {
		departure = getDepartureFromResultSet(rs);
		result = Optional.of(departure);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return result;
    }

    private Departure getDepartureFromResultSet(ResultSet rs) {
	Departure departure = null;
	try {
	    departure = new Departure.Builder().withId(rs.getInt("d_id"))
		    .withDateTtime(rs.getTimestamp("d_datetime").toLocalDateTime()).withTrain(getTrainFromResultSet(rs))
		    .build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Departure.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return departure;
    }

    private Train getTrainFromResultSet(ResultSet rs) {
	Train train = null;
	try {
	    train = new Train.Builder().withId(rs.getInt("tr_id")).withName(rs.getString("tr_name")).build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Train.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return train;
    }

    @Override
    public List<Departure> findAll() {
	List<Departure> sheduleList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_DEPARTURES)) {
	    ResultSet rs = query.executeQuery();
	    Departure departure;
	    while (rs.next()) {
		departure = getDepartureFromResultSet(rs);
		sheduleList.add(departure);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return sheduleList;
    }

    @Override
    public void create(Departure departure) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_DEPARTURE, Statement.RETURN_GENERATED_KEYS)) {
	    query.setTimestamp(1, Timestamp.valueOf(departure.getDateTime()));
	    query.setInt(2, departure.getTrain().getId());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		departure.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}

    }

    @Override
    public void update(Departure departure) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_DEPARTURE)) {
	    query.setTimestamp(1, Timestamp.valueOf(departure.getDateTime()));
	    query.setInt(2, departure.getTrain().getId());
	    query.setInt(3, departure.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_UPDATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_DEPARTURE_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_DELETE_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

}
