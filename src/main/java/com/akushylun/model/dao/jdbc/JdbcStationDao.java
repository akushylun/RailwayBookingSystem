package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.StationDao;
import com.akushylun.model.entity.Station;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcStationDao implements StationDao {

    private static final String SELECT_STATION_BY_ID = "SELECT s.st_id, s.st_from, s.st_to FROM station as s"
	    + " WHERE s.st_id = ?";
    private static final String SELECT_ALL_STATIONS = "SELECT s.st_id, s.st_from, s.st_to FROM station as s";
    private static final String CREATE_STATION = "INSERT INTO station (st_from, st_to) " + " VALUES (?,?)";
    private static final String UPDATE_STATION = "UPDATE station SET st_from = ?, st_to = ?" + " WHERE st_id = ?";
    private static final String DELETE_STATION_BY_ID = "DELETE FROM station WHERE st_id = ?";

    private final boolean connectionShouldBeClosed;
    private Connection connection;

    public JdbcStationDao(Connection connection) {
	this.connection = connection;
	connectionShouldBeClosed = false;
    }

    private Station getStationFromResultSet(ResultSet rs) throws SQLException {
	Station station = new Station.Builder().withId(rs.getInt("st_id")).from(rs.getString("st_from"))
		.to(rs.getString("st_to")).build();
	return station;
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
	    throw new RuntimeException(ex);
	}
	return result;
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
	    throw new RuntimeException(ex);
	}
	return trainList;
    }

    @Override
    public void create(Station station) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_STATION, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, station.getFrom());
	    query.setString(2, station.getTo());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		station.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

    }

    @Override
    public void update(Station station) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_STATION)) {
	    query.setString(1, station.getFrom());
	    query.setString(2, station.getTo());
	    query.setInt(3, station.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_STATION_BY_ID)) {
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
