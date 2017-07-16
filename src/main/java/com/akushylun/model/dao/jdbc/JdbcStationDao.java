package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.akushylun.controller.util.LogMessage;
import com.akushylun.model.dao.StationDao;
import com.akushylun.model.entities.Station;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcStationDao implements StationDao {

    private static final String SELECT_STATION_BY_ID = "SELECT s.st_id, s.st_name FROM station as s WHERE s.st_id = ?";
    private static final String SELECT_ALL_STATIONS = "SELECT s.st_id, s.st_name FROM station as s";
    private static final String CREATE_STATION = "INSERT INTO station (st_name) " + " VALUES (?)";
    private static final String UPDATE_STATION = "UPDATE station SET st_name = ? " + " WHERE st_id = ?";
    private static final String DELETE_STATION_BY_ID = "DELETE FROM station WHERE st_id = ?";

    private static final Logger LOGGER = Logger.getLogger(JdbcStationDao.class);
    private Connection connection;

    public JdbcStationDao(Connection connection) {
	this.connection = connection;
    }

    @Override
    public Optional<Station> find(int id) {
	Optional<Station> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_STATION_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Station station;
	    if (rs.next()) {
		station = getStationFromResultSet(rs);
		result = Optional.of(station);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return result;
    }

    private Station getStationFromResultSet(ResultSet rs) {
	Station station;
	try {
	    station = new Station.Builder().withId(rs.getInt("st_id")).withName(rs.getString("st_name")).build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Station.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return station;
    }

    @Override
    public List<Station> findAll() {
	List<Station> trainList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_STATIONS)) {
	    ResultSet rs = query.executeQuery();
	    Station station;
	    while (rs.next()) {
		station = getStationFromResultSet(rs);
		trainList.add(station);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return trainList;
    }

    @Override
    public void create(Station station) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_STATION, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, station.getName());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		station.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}

    }

    @Override
    public void update(Station station) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_STATION)) {
	    query.setString(1, station.getName());
	    query.setInt(2, station.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_UPDATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_STATION_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_DELETE_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

}
