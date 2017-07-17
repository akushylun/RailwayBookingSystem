package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.akushylun.controller.util.LogMessage;
import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.TrainStation;

public class JdbcTicketDao implements TicketDao {

    private static final String SELECT_TICKET_BY_ID = "SELECT ti.ti_id, ti.ti_description FROM ticket as ti WHERE ti.ti._id = ?";
    private static final String SELECT_ALL_TICKETS = "SELECT ti.ti_id, ti.ti_description FROM ticket as ti";
    private static final String CREATE_TICKET = "INSERT INTO ticket (ti_description) " + "VALUES (?)";
    private static final String CREATE_M2M_TICKET_TRAIN_STATION = "INSERT INTO m2m_ticket_train_station (m2m_ticket_ti_id, m2m_m2m_train_station_id) "
	    + "VALUES (?,(SELECT m2m_train_station_id FROM m2m_train_station WHERE m2m_train_tr_id = ? AND m2m_station_st_id = ?))";
    private static final String UPDATE_TICKET = "UPDATE ticket SET ti_description = ? WHERE ti_id = ?";
    private static final String DELETE_TICKET_BY_ID = "DELETE FROM ticket WHERE ti_id = ?";

    private static final Logger LOGGER = Logger.getLogger(JdbcTicketDao.class);
    private Connection connection;

    public JdbcTicketDao(Connection connection) {
	this.connection = connection;
    }

    @Override
    public Optional<Ticket> find(int id) {
	Optional<Ticket> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_TICKET_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Ticket Ticket;
	    if (rs.next()) {
		Ticket = getTicketFromResultSet(rs);
		result = Optional.of(Ticket);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return result;
    }

    private Ticket getTicketFromResultSet(ResultSet rs) {
	Ticket ticket;
	try {
	    ticket = new Ticket.Builder().withId(rs.getInt("ti_id")).withDescription(rs.getString("ti_description"))
		    .build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Ticket.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return ticket;
    }

    @Override
    public List<Ticket> findAll() {
	List<Ticket> ticketList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_TICKETS)) {
	    ResultSet rs = query.executeQuery();
	    Ticket ticket = null;
	    while (rs.next()) {
		if (ticketList.contains(rs.getInt("ti_id")) == false) {
		    ticket = getTicketFromResultSet(rs);
		    ticketList.add(ticket);
		}
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return ticketList;
    }

    @Override
    public void create(Ticket ticket) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_TICKET, Statement.RETURN_GENERATED_KEYS)) {
	    query.setString(1, ticket.getDescription());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		ticket.setId(keys.getInt(1));
	    }
	    createM2MTicketTrainStation(ticket, ticket.getTrainStationList().get(0));
	    createM2MTicketTrainStation(ticket, ticket.getTrainStationList().get(1));
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    private void createM2MTicketTrainStation(Ticket ticket, TrainStation trainStation) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_M2M_TICKET_TRAIN_STATION,
		Statement.RETURN_GENERATED_KEYS)) {
	    query.setInt(1, ticket.getId());
	    query.setInt(2, trainStation.getTrain().getId());
	    query.setInt(3, trainStation.getStation().getId());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		ticket.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void update(Ticket ticket) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_TICKET)) {
	    query.setString(1, ticket.getDescription());
	    query.setInt(2, ticket.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_UPDATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_TICKET_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_DELETE_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }
}
