package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.entities.Shedule;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;

public class JdbcTicketDao implements TicketDao {

    private static final String SELECT_TICKET_BY_ID = "SELECT t.ti_id, t.ti_price, s.sh_id, s.sh_start, s.sh_end FROM ticket as t "
	    + "INNER JOIN shedule as s ON t.ti_shedule_sh_id = s.sh_id WHERE t.ti_id = ?";
    private static final String SELECT_ALL_TICKETS = "SELECT t.ti_id, t.ti_price, s.sh_id, s.sh_start, s.sh_end FROM ticket as t "
	    + "INNER JOIN shedule as s ON t.ti_shedule_sh_id = s.sh_id";
    private static final String SELECT_ALL_TICKETS_BY_BOOKING_ID = "SELECT m2m.m2m_ticket_ti_id, t.ti_id, t.ti_price, s.sh_id, s.sh_start, "
	    + "s.sh_end, st_id, st_from, st_to, tr_id, tr_name FROM ticket as t INNER JOIN m2m_booking_ticket as m2m "
	    + "ON m2m.m2m_ticket_ti_id = m2m.m2m_booking_b_id INNER JOIN shedule as s ON t.ti_shedule_sh_id = s.sh_id INNER JOIN train "
	    + "as tr ON s.sh_train_tr_id = tr.tr_id INNER JOIN station as st ON s.sh_station_st_id = st.st_id WHERE m2m.m2m_booking_b_id = ?";
    private static final String CREATE_TICKET = "INSERT INTO ticket (ti_price, ti_shedule_sh_id) " + "VALUES (?,?)";
    private static final String UPDATE_TICKET = "UPDATE ticket SET ti_price = ?, ti_shedule_sh_id = ? WHERE ti_id = ?";
    private static final String DELETE_TICKET_BY_ID = "DELETE FROM ticket WHERE ti_id = ?";

    private final boolean connectionShouldBeClosed;
    private Connection connection;

    public JdbcTicketDao(Connection connection, boolean connectionShouldBeClosed) {
	this.connection = connection;
	this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    private Ticket getTicketFromResultSet(ResultSet rs) throws SQLException {
	Ticket Ticket = new Ticket.Builder().withId(rs.getInt("ti_id")).withPrice(rs.getBigDecimal("ti_price"))
		.withShedule(getSheduleFromResultSet(rs)).build();
	return Ticket;
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
	    throw new RuntimeException(ex);
	}
	return result;
    }

    @Override
    public List<Ticket> findAll() {
	List<Ticket> ticketList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_TICKETS)) {
	    ResultSet rs = query.executeQuery();
	    Ticket ticket;
	    while (rs.next()) {
		ticket = getTicketFromResultSet(rs);
		ticketList.add(ticket);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return ticketList;
    }

    @Override
    public List<Ticket> findAllByBookingId(int bookingId) {
	List<Ticket> ticketList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_TICKETS_BY_BOOKING_ID)) {
	    query.setInt(1, bookingId);
	    ResultSet rs = query.executeQuery();
	    Ticket ticket;
	    while (rs.next()) {
		ticket = getTicketFromResultSet(rs);
		ticketList.add(ticket);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return ticketList;
    }

    @Override
    public void create(Ticket ticket) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_TICKET, Statement.RETURN_GENERATED_KEYS)) {
	    query.setBigDecimal(1, ticket.getPrice());
	    query.setInt(2, ticket.getShedule().getId());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		ticket.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void update(Ticket ticket) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_TICKET)) {
	    query.setBigDecimal(1, ticket.getPrice());
	    query.setInt(2, ticket.getShedule().getId());
	    query.setInt(3, ticket.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_TICKET_BY_ID)) {
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
