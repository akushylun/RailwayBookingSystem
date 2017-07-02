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

import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;
import com.akushylun.model.entities.Ticket;

public class JdbcBookingDao implements BookingDao {

    private static final String SELECT_BOOKING_BY_ID = "SELECT b.b_id, b.b_price, b.b_date, p.p_id, p.p_name, p.p_surname, p.p_email, "
	    + "p.p_role_r_name, t.ti_id, t.ti_price FROM booking as b INNER JOIN person as p ON p.p_id = b.b_person_p_id "
	    + "INNER JOIN m2m_booking_ticket as m2m ON b.b_id = m2m.m2m_booking_b_id "
	    + "INNER JOIN ticket as t ON m2m.m2m_ticket_ti_id = t.ti_id " + "WHERE b.b_id = ?";
    private static final String SELECT_ALL_BOOKINGS = "SELECT b.b_id, b.b_price, b.b_date, p.p_id, p.p_name, p.p_surname, p.p_email, "
	    + "p.p_role_r_name, t.ti_id, t.ti_price FROM booking as b INNER JOIN person as p ON p.p_id = b.b_person_p_id "
	    + "INNER JOIN m2m_booking_ticket as m2m ON b.b_id = m2m.m2m_booking_b_id "
	    + "INNER JOIN ticket as t ON m2m.m2m_ticket_ti_id = t.ti_id ";
    private static final String SELECT_ALL_BOOKINGS_BY_PERSON_ID = "SELECT b.b_id, b.b_price, b.b_date, p.p_id, p.p_name, p.p_surname, p.p_email, "
	    + "p.p_role_r_name, t.ti_id, t.ti_price FROM booking as b INNER JOIN person as p ON p.p_id = b.b_person_p_id "
	    + "INNER JOIN m2m_booking_ticket as m2m ON b.b_id = m2m.m2m_booking_b_id "
	    + "INNER JOIN ticket as t ON m2m.m2m_ticket_ti_id = t.ti_id WHERE p.p_id = ?";
    private static final String CREATE_BOOKING = "INSERT INTO booking (b_price, b_date, b_person_p_id) SELECT ?,?,p_id FROM person "
	    + "WHERE p_email = ?";
    private static final String CREATE_M2M_BOOKING_TICKETS = "INSERT INTO m2m_booking_ticket (m2m_booking_b_id, m2m_ticket_ti_id) "
	    + "VALUES(?,?)";
    private static final String UPDATE_BOOKING = "UPDATE booking SET b_price = ?, b_date = ? WHERE b_id = ?";
    private static final String DELETE_BOOKING_BY_ID = "DELETE FROM booking WHERE b_id = ?";

    private final boolean connectionShouldBeClosed;
    private Connection connection;

    public JdbcBookingDao(Connection connection, boolean connectionShouldBeClosed) {
	this.connection = connection;
	this.connectionShouldBeClosed = connectionShouldBeClosed;
    }

    private Booking getBookingFromResultSet(ResultSet rs) throws SQLException {
	Booking booking = new Booking.Builder().withId(rs.getInt("b_id")).withPrice(rs.getBigDecimal("b_price"))
		.withDate(rs.getTimestamp("b_date").toLocalDateTime()).withUser(getUserFromResultSet(rs))
		.withTickets(getTicketsFromResultSet(rs)).build();
	return booking;
    }

    private Person getUserFromResultSet(ResultSet rs) {
	Person person = null;
	try {
	    if (rs.getString("p_email") != null) {
		person = new Person.Builder().withId(rs.getInt("p_id")).withName(rs.getString("p_name"))
			.withSurname(rs.getString("p_surname")).withEmail(rs.getString("p_email"))
			.withRole(Role.valueOf(rs.getString("p_role_r_name").toUpperCase())).build();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return person;
    }

    private List<Ticket> getTicketsFromResultSet(ResultSet rs) {
	List<Ticket> ticketsList = new ArrayList<>();
	Ticket ticket = null;
	try {
	    if (rs.getInt("ti_id") != 0) {
		ticket = new Ticket.Builder().withId(rs.getInt("ti_id")).withPrice(rs.getBigDecimal("ti_price"))
			.build();
		ticketsList.add(ticket);
		rs.next();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return ticketsList;
    }

    @Override
    public Optional<Booking> find(int id) {
	Optional<Booking> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_BOOKING_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Booking booking;
	    if (rs.next()) {
		booking = getBookingFromResultSet(rs);
		result = Optional.of(booking);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return result;
    }

    @Override
    public List<Booking> findAllByUserId(int userId) {
	List<Booking> bookingList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_BOOKINGS_BY_PERSON_ID)) {
	    query.setInt(1, userId);
	    ResultSet rs = query.executeQuery();
	    Booking booking;
	    while (rs.next()) {
		booking = getBookingFromResultSet(rs);
		bookingList.add(booking);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return bookingList;
    }

    @Override
    public List<Booking> findAll() {
	List<Booking> bookingList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_BOOKINGS)) {
	    ResultSet rs = query.executeQuery();
	    Booking booking;
	    while (rs.next()) {
		booking = getBookingFromResultSet(rs);
		bookingList.add(booking);
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
	return bookingList;
    }

    @Override
    public void create(Booking booking) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_BOOKING, Statement.RETURN_GENERATED_KEYS)) {
	    query.setBigDecimal(1, booking.getPrice());
	    query.setTimestamp(2, Timestamp.valueOf(booking.getDate()));
	    query.setString(3, booking.getUser().getEmail());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		booking.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void createBookingTicketsLink(Booking booking) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_M2M_BOOKING_TICKETS)) {
	    query.setInt(1, booking.getId());
	    List<Ticket> tickets = booking.getTickets();
	    for (Ticket ticket : tickets) {
		int ticketId = ticket.getId();
		query.setInt(2, ticketId);
		query.executeUpdate();
	    }
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void update(Booking booking) {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_BOOKING)) {
	    query.setBigDecimal(1, booking.getPrice());
	    query.setTimestamp(2, Timestamp.valueOf(booking.getDate()));
	    query.setInt(3, booking.getId());
	    query.executeUpdate();
	} catch (SQLException ex) {
	    throw new RuntimeException(ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_BOOKING_BY_ID)) {
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
