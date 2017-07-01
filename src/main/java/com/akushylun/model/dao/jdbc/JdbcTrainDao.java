package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.TrainDao;
import com.akushylun.model.entities.Train;
import com.mysql.cj.api.jdbc.Statement;

public class JdbcTrainDao implements TrainDao {

    private static final String SELECT_TRAIN_BY_ID = "SELECT t.tr_id, t.tr_name FROM train as t" + " WHERE t.tr_id = ?";
    private static final String SELECT_ALL_TRAINS = "SELECT t.tr_id, t.tr_name FROM train as t";
    private static final String CREATE_TRAIN = "INSERT INTO train (tr_name) " + " VALUES (?)";
    private static final String UPDATE_TRAIN = "UPDATE train SET tr_name = ?" + " WHERE tr_id = ?";
    private static final String DELETE_TRAIN_BY_ID = "DELETE FROM train WHERE tr_id = ?";

    private final boolean connectionShouldBeClosed;
    private Connection connection;

    public JdbcTrainDao(Connection connection, boolean connectionShouldBeClosed) {
	this.connection = connection;
	this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    private Train getTrainFromResultSet(ResultSet rs) throws SQLException {
	Train train = new Train.Builder().withId(rs.getInt("tr_id")).withName(rs.getString("tr_name")).build();
	return train;
    }

    @Override
    public Optional<Train> find(int id) {
	Optional<Train> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_TRAIN_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Train train;
	    if (rs.next()) {
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
