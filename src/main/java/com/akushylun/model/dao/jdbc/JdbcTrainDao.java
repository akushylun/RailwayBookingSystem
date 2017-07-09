package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.TrainDao;
import com.akushylun.model.entities.Departure;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Train;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcTrainDao implements TrainDao {

    private static final String SELECT_TRAIN_BY_ID = "SELECT tr.tr_id, tr.tr_name, s.st_id, s.st_name, d.d_id, d.d_datetime, "
	    + "m2m.m2m_cost_time FROM train as tr INNER JOIN departure as d ON tr.tr_id = d.d_id INNER JOIN m2m_train_station as m2m "
	    + "ON tr.tr_id = m2m.m2m_train_tr_id INNER JOIN station as s ON m2m.m2m_station_st_id = s.st_id WHERE tr.tr_id = ? ORDER BY "
	    + "m2m_cost_time";
    private static final String SELECT_ALL_TRAINS = "SELECT tr.tr_id, tr.tr_name, s.st_id, s.st_name, d.d_id, d.d_datetime, "
	    + "m2m.m2m_cost_time FROM train as tr INNER JOIN departure as d ON tr.tr_id = d.d_id INNER JOIN m2m_train_station as m2m "
	    + "ON tr.tr_id = m2m.m2m_train_tr_id INNER JOIN station as s ON m2m.m2m_station_st_id = s.st_id ORDER BY "
	    + "tr.tr_id, m2m_cost_time";
    private static final String CREATE_TRAIN = "INSERT INTO train (tr_name) " + " VALUES (?)";
    private static final String UPDATE_TRAIN = "UPDATE train SET tr_name = ? WHERE tr_id = ?";
    private static final String DELETE_TRAIN_BY_ID = "DELETE FROM train WHERE tr_id = ?";

    private final boolean connectionShouldBeClosed;
    private Connection connection;

    public JdbcTrainDao(Connection connection, boolean connectionShouldBeClosed) {
	this.connection = connection;
	this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    private Train getTrainFromResultSet(ResultSet rs) throws SQLException {
	Train train = new Train.Builder().withId(rs.getInt("tr_id")).withName(rs.getString("tr_name"))
		.withDepartureList(getDepartureFromResultSet(rs)).withStationList(getStationFromResultSet(rs)).build();
	return train;
    }

    private List<Station> getStationFromResultSet(ResultSet rs) throws SQLException {
	List<Station> stationList = new ArrayList<>();
	Station station = null;
	try {
	    int trainId = rs.getInt("tr_id");
	    ResultSet result = rs;
	    boolean flag = true;
	    while ((flag == true) && containsStation(result, trainId)) {
		station = new Station.Builder().withId(rs.getInt("st_id")).withName(rs.getString("st_name")).build();
		stationList.add(station);
		flag = rs.next();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return stationList;
    }

    private boolean containsStation(ResultSet rs, int trainId) {
	boolean contains;
	try {
	    if (rs.getInt("tr_id") == trainId) {
		contains = true;
	    } else {
		contains = false;
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return contains;
    }

    private List<Departure> getDepartureFromResultSet(ResultSet rs) {
	List<Departure> departureList = new ArrayList<>();
	Departure departure = null;
	try {
	    if (rs.getInt("d_id") != 0) {
		departure = new Departure.Builder().withId(rs.getInt("d_id"))
			.withDateTtime(rs.getTimestamp("d_datetime").toLocalDateTime()).build();
		departureList.add(departure);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return departureList;
    }

    @Override
    public Optional<Train> find(int id) {
	Optional<Train> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_TRAIN_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Train train;
	    while (rs.next()) {
		train = getTrainFromResultSet(rs);
		result = Optional.of(train);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
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
		train = getTrainFromResultSet(rs);
		trainList.add(train);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return trainList;
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
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void update(Train train) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_TRAIN)) {
	    query.setString(1, train.getName());
	    query.setInt(2, train.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_TRAIN_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void close() throws Exception {
	if (connectionShouldBeClosed) {
	    try {
		connection.close();
	    } catch (SQLException e) {
		throw new RuntimeException(e);
	    }
	}
    }
}
