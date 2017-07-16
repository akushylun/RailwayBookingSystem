package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.akushylun.controller.util.LogMessage;
import com.akushylun.model.dao.TrainDao;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Train;
import com.akushylun.model.entities.TrainStation;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcTrainDao implements TrainDao {

    private static final String SELECT_TRAIN_BY_ID = "SELECT station.st_id as st_id_start, st2.st_id as st_id_end, station.st_name as "
	    + "st_name_start, st2.st_name as st_name_end, train.tr_id, train.tr_name, DATE_ADD(departure.d_datetime, "
	    + "INTERVAL m2m.m2m_cost_time MINUTE) as start_time, DATE_ADD(departure.d_datetime, INTERVAL (m2m2.m2m_cost_time - "
	    + "m2m.m2m_cost_time) MINUTE) as end_time, m2m.m2m_cost_price as cost_price_start, m2m2.m2m_cost_price as cost_price_end "
	    + "FROM train JOIN departure ON tr_id = d_train_tr_id JOIN m2m_train_station as m2m ON tr_id = m2m.m2m_train_tr_id JOIN station "
	    + "ON station.st_id = m2m.m2m_station_st_id JOIN m2m_train_station as m2m2 ON tr_id = m2m2.m2m_train_tr_id JOIN station as st2 "
	    + "ON st2.st_id = m2m2.m2m_station_st_id WHERE tr_id = ? AND m2m2.m2m_cost_price > m2m.m2m_cost_price AND m2m2.m2m_cost_time > "
	    + "m2m.m2m_cost_time";
    private static final String SELECT_ALL_TRAINS = "SELECT station.st_id as st_id_start, st2.st_id as st_id_end, station.st_name as "
	    + "st_name_start, st2.st_name as st_name_end, train.tr_id, train.tr_name, DATE_ADD(departure.d_datetime,  "
	    + "INTERVAL m2m.m2m_cost_time MINUTE) as start_time, DATE_ADD(departure.d_datetime, INTERVAL (m2m2.m2m_cost_time - m2m.m2m_cost_time) MINUTE) "
	    + "as end_time, m2m.m2m_cost_price as cost_price_start, m2m2.m2m_cost_price as cost_price_end FROM train JOIN departure ON "
	    + "tr_id = d_train_tr_id JOIN m2m_train_station as m2m ON tr_id = m2m.m2m_train_tr_id JOIN station ON station.st_id = "
	    + "m2m.m2m_station_st_id JOIN m2m_train_station as m2m2 ON tr_id = m2m2.m2m_train_tr_id JOIN station as st2 ON st2.st_id = m2m2.m2m_station_st_id WHERE m2m2.m2m_cost_price > m2m.m2m_cost_price AND m2m2.m2m_cost_time > m2m.m2m_cost_time; ";
    private static final String SELECT_ALL_BY_STATION_START_END_DATE = "SELECT station.st_id as st_id_start, st2.st_id as st_id_end, "
	    + "station.st_name as st_name_start, st2.st_name as st_name_end, train.tr_id, train.tr_name, DATE_ADD(departure.d_datetime, "
	    + "INTERVAL m2m.m2m_cost_time MINUTE) as start_time, DATE_ADD(departure.d_datetime, INTERVAL (m2m2.m2m_cost_time - "
	    + "m2m.m2m_cost_time) MINUTE) as end_time, m2m.m2m_cost_price as cost_price_start, m2m2.m2m_cost_price as cost_price_end  "
	    + "FROM train JOIN departure ON tr_id = d_train_tr_id JOIN m2m_train_station as m2m ON tr_id = m2m.m2m_train_tr_id JOIN station "
	    + "ON station.st_id = m2m.m2m_station_st_id JOIN m2m_train_station as m2m2 ON tr_id = m2m2.m2m_train_tr_id JOIN station as st2 "
	    + "ON st2.st_id = m2m2.m2m_station_st_id WHERE station.st_name  = ? AND st2.st_name = ? AND cast(departure.d_datetime as DATE) = ?";
    private static final String CREATE_TRAIN = "INSERT INTO train (tr_name) " + " VALUES (?)";
    private static final String UPDATE_TRAIN = "UPDATE train SET tr_name = ? WHERE tr_id = ?";
    private static final String DELETE_TRAIN_BY_ID = "DELETE FROM train WHERE tr_id = ?";

    private static final Logger LOGGER = Logger.getLogger(JdbcTrainDao.class);
    private Connection connection;

    public JdbcTrainDao(Connection connection) {
	this.connection = connection;
    }

    @Override
    public Optional<Train> find(int id) {
	Optional<Train> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_TRAIN_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Train train;
	    while (rs.next()) {
		train = extractTrainFromResultSet(rs);
		result = Optional.of(train);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return result;
    }

    @Override
    public List<Train> findAll() {
	List<Train> trainList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_TRAINS)) {
	    ResultSet rs = query.executeQuery();
	    Train train;
	    while (rs.next()) {
		train = extractTrainFromResultSet(rs);
		trainList.add(train);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return trainList;
    }

    @Override
    public List<Train> findAll(String stationStart, String stationEnd, LocalDate startDate) {
	List<Train> trainList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_BY_STATION_START_END_DATE)) {
	    query.setString(1, stationStart);
	    query.setString(2, stationEnd);
	    query.setString(3, startDate.toString());
	    ResultSet rs = query.executeQuery();
	    Train train;
	    while (rs.next()) {
		train = extractTrainFromResultSet(rs);
		trainList.add(train);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL_BY_PARAMS;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return trainList;
    }

    private Train extractTrainFromResultSet(ResultSet rs) {
	Train train;
	try {
	    train = new Train.Builder().withId(rs.getInt("tr_id")).withName(rs.getString("tr_name"))
		    .withStationList(getStationListFromResultSet(rs)).build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL_BY_PARAMS;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return train;
    }

    private List<TrainStation> getStationListFromResultSet(ResultSet rs) {
	List<TrainStation> trainStationList = new ArrayList<>();
	try {
	    if (rs.getInt("st_id_start") != 0 && rs.getInt("st_id_end") != 0) {
		TrainStation trainStationFrom = new TrainStation.Builder()
			.withDateTime(rs.getTimestamp("start_time").toLocalDateTime())
			.withPrice(rs.getBigDecimal("cost_price_start")).withStation(getStationFromResultSet(rs))
			.build();
		TrainStation trainStationTo = new TrainStation.Builder()
			.withDateTime(rs.getTimestamp("end_time").toLocalDateTime())
			.withPrice(rs.getBigDecimal("cost_price_end")).withStation(getStationToResultSet(rs)).build();
		trainStationList.add(trainStationFrom);
		trainStationList.add(trainStationTo);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL_BY_PARAMS;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return trainStationList;
    }

    private Station getStationToResultSet(ResultSet rs) {
	Station stationTo;
	try {
	    stationTo = new Station.Builder().withId(rs.getInt("st_id_end")).withName(rs.getString("st_name_end"))
		    .build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Station.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return stationTo;
    }

    private Station getStationFromResultSet(ResultSet rs) {
	Station stationFrom;
	try {
	    stationFrom = new Station.Builder().withId(rs.getInt("st_id_start")).withName(rs.getString("st_name_start"))
		    .build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Station.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return stationFrom;
    }

    @Override
    public void create(Train train) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_TRAIN, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, train.getName());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		train.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void update(Train train) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_TRAIN)) {
	    query.setString(1, train.getName());
	    query.setInt(2, train.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_UPDATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_TRAIN_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_DELETE_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }
}
