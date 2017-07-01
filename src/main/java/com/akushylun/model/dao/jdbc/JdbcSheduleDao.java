package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.SheduleDao;
import com.akushylun.model.entities.Shedule;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Train;

public class JdbcSheduleDao implements SheduleDao {

    private static final String SELECT_SHEDULE_BY_ID = "SELECT sh.sh_id, sh.sh_start, sh.sh_end, st.st_id, st.st_from, st.st_to,tr.tr_id, "
	    + "tr.tr_name FROM shedule as sh INNER JOIN station as st ON sh.sh_station_st_id = st.st_id INNER JOIN train as tr "
	    + "ON sh.sh_train_tr_id = tr.tr_id WHERE sh.sh_id = ?";
    private static final String SELECT_ALL_SHEDULES_BY_DATE = "SELECT sh.sh_id, sh.sh_start, sh.sh_end, st.st_id, st.st_from, st.st_to,"
	    + "tr.tr_id,tr.tr_name FROM shedule as sh INNER JOIN station as st ON sh.sh_station_st_id = st.st_id INNER JOIN train as tr "
	    + "ON sh.sh_train_tr_id = tr.tr_id WHERE st.st_from =? AND st.st_to =? AND (SELECT date(sh_start)) = ?";
    private static final String SELECT_ALL_SHEDULES = "SELECT sh.sh_id, sh.sh_start, sh.sh_end, st.st_id, st.st_from, st.st_to,tr.tr_id, "
	    + "tr.tr_name FROM shedule as sh INNER JOIN station as st ON sh.sh_station_st_id = st.st_id INNER JOIN train as tr "
	    + "ON sh.sh_train_tr_id = tr.tr_id";
    private static final String CREATE_SHEDULE = "INSERT INTO shedule (sh_start, sh_end, sh_station_st_id, sh_train_tr_id) "
	    + "VALUES (?,?,?,?)";
    private static final String UPDATE_SHEDULE = "UPDATE shedule SET sh_start = ?, sh_end = ? WHERE sh_id= ?";
    private static final String DELETE_SHEDULE_BY_ID = "DELETE FROM shedule WHERE sh_id = ?";

    private final boolean connectionShouldBeClosed;
    private Connection connection;

    public JdbcSheduleDao(Connection connection, boolean connectionShouldBeClosed) {
	this.connection = connection;
	this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    private Shedule getSheduleFromResultSet(ResultSet rs) {
	Shedule shedule = null;
	try {
	    shedule = new Shedule.Builder().withId(rs.getInt("sh_id"))
		    .start(rs.getTimestamp("sh_start").toLocalDateTime())
		    .end(rs.getTimestamp("sh_end").toLocalDateTime()).withStation(getStationFromResultSet(rs))
		    .withTrain(getTrainFromResultSet(rs)).build();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return shedule;
    }

    private Train getTrainFromResultSet(ResultSet rs) {
	Train train = null;
	try {
	    train = new Train.Builder().withId(rs.getInt("tr_id")).withName(rs.getString("tr_name")).build();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return train;
    }

    private Station getStationFromResultSet(ResultSet rs) {
	Station station = null;
	try {
	    station = new Station.Builder().withId(rs.getInt("st_id")).from(rs.getString("st_from"))
		    .to(rs.getString("st_to")).build();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return station;
    }

    @Override
    public Optional<Shedule> find(int id) {
	Optional<Shedule> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_SHEDULE_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Shedule shedule;
	    if (rs.next()) {
		shedule = getSheduleFromResultSet(rs);
		result = Optional.of(shedule);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return result;
    }

    @Override
    public List<Shedule> findAllByDate(LocalDateTime date) {
	List<Shedule> sheduleList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_SHEDULES_BY_DATE)) {
	    query.setTimestamp(1, Timestamp.valueOf(date));
	    ResultSet rs = query.executeQuery();
	    Shedule shedule;
	    while (rs.next()) {
		shedule = getSheduleFromResultSet(rs);
		sheduleList.add(shedule);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return sheduleList;
    }

    @Override
    public List<Shedule> findAll() {
	List<Shedule> sheduleList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_SHEDULES)) {
	    ResultSet rs = query.executeQuery();
	    Shedule shedule;
	    while (rs.next()) {
		shedule = getSheduleFromResultSet(rs);
		sheduleList.add(shedule);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return sheduleList;
    }

    @Override
    public void create(Shedule shedule) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_SHEDULE, Statement.RETURN_GENERATED_KEYS)) {
	    query.setTimestamp(1, Timestamp.valueOf(shedule.getStart()));
	    query.setTimestamp(2, Timestamp.valueOf(shedule.getEnd()));
	    query.setInt(3, shedule.getStation().getId());
	    query.setInt(4, shedule.getTrain().getId());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		shedule.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}

    }

    @Override
    public void update(Shedule shedule) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_SHEDULE)) {
	    query.setTimestamp(1, Timestamp.valueOf(shedule.getStart()));
	    query.setTimestamp(2, Timestamp.valueOf(shedule.getEnd()));
	    query.setInt(3, shedule.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_SHEDULE_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void close() {
	if (connectionShouldBeClosed) {
	    try {
		connection.close();
	    } catch (SQLException e) {
		throw new RuntimeException(e);
	    }
	}
    }
}
