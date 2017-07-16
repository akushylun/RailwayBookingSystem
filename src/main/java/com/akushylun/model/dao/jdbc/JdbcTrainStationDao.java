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
import com.akushylun.model.dao.TrainStationDao;
import com.akushylun.model.entities.TrainStation;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcTrainStationDao implements TrainStationDao {

    private static final String SELECT_TRAIN_STATION_BY_ID = "SELECT m2m_train_station_id, m2m_train_station.m2m_cost_price, m2m_train_station.m2m_cost_time "
	    + "FROM m2m_train_station WHERE m2m_train_station_id = ?";
    private static final String SELECT_ALL_TRAINS_STATIONS = "SELECT m2m_train_station_id, m2m_train_station.m2m_cost_price, m2m_train_station.m2m_cost_time "
	    + "FROM m2m_train_station";
    private static final String CREATE_TRAIN_STATION = "INSERT INTO m2m_train_station (m2m_cost_time, m2m_cost_price) SELECT ?,?";
    private static final String UPDATE_TRAIN_STATION = "UPDATE m2m_train_station SET m2m_cost_time = ?, m2m_cost_price = ?"
	    + " WHERE m2m_train_station_id = ?";
    private static final String DELETE_TRAIN_STATION_BY_ID = "DELETE FROM m2m_train_station WHERE m2m_train_station_id = ?";

    private static final Logger LOGGER = Logger.getLogger(JdbcTrainStationDao.class);
    private Connection connection;

    public JdbcTrainStationDao(Connection connection) {
	this.connection = connection;
    }

    @Override
    public Optional<TrainStation> find(int id) {
	Optional<TrainStation> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_TRAIN_STATION_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    TrainStation trainStation;
	    if (rs.next()) {
		trainStation = getTrainStationFromResultSet(rs);
		result = Optional.of(trainStation);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return result;
    }

    private TrainStation getTrainStationFromResultSet(ResultSet rs) {
	TrainStation trainStation = null;
	try {
	    if (rs.getInt("m2m_train_station_id") != 0) {
		trainStation = new TrainStation.Builder().withId(rs.getInt("m2m_train_station_id"))
			.withDateTime(rs.getTimestamp("m2m_cost_time").toLocalDateTime())
			.withPrice(rs.getBigDecimal("m2m_cost_price")).build();
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + TrainStation.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return trainStation;
    }

    @Override
    public List<TrainStation> findAll() {
	List<TrainStation> trainStationList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_TRAINS_STATIONS)) {
	    ResultSet rs = query.executeQuery();
	    TrainStation trainStation;
	    while (rs.next()) {
		trainStation = getTrainStationFromResultSet(rs);
		trainStationList.add(trainStation);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return trainStationList;
    }

    @Override
    public void create(TrainStation trainStation) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_TRAIN_STATION,
		Statement.RETURN_GENERATED_KEYS)) {
	    query.setInt(1, trainStation.getId());
	    query.setBigDecimal(2, trainStation.getCost_price());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		trainStation.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void update(TrainStation trainStation) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_TRAIN_STATION)) {
	    query.setInt(1, trainStation.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_UPDATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_TRAIN_STATION_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_DELETE_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

}
