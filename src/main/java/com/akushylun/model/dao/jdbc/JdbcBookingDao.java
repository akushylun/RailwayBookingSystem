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

import org.apache.log4j.Logger;

import com.akushylun.controller.util.LogMessage;
import com.akushylun.model.dao.BookingDao;
import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;
import com.akushylun.model.entities.TrainStation;

public class JdbcBookingDao implements BookingDao {

    private static final String SELECT_BOOKING_BY_ID = "SELECT b.b_id, b.b_price, b.b_date, b.b_ticket_ti_id, ti.ti_id, ti.ti_description, tr.tr_id, tr.tr_name, m2m_ts.m2m_cost_price, st.st_id, st.st_name, DATE_ADD(d.d_datetime, INTERVAL m2m_ts.m2m_cost_time MINUTE) as m2m_cost_time "
	    + "FROM booking as b JOIN person as p ON p.p_id = b.b_person_p_id JOIN ticket as ti ON b.b_ticket_ti_id = ti.ti_id JOIN "
	    + "m2m_ticket_train_station as m2m_tts ON ti.ti_id = m2m_tts.m2m_ticket_ti_id JOIN m2m_train_station as m2m_ts ON m2m_tts.m2m_m2m_train_station_id = m2m_ts.m2m_train_station_id "
	    + "JOIN train as tr ON tr.tr_id = m2m_ts.m2m_train_tr_id JOIN departure as d ON d.d_train_tr_id = tr.tr_id JOIN station as st "
	    + "ON m2m_ts.m2m_station_st_id = st.st_id" + "WHERE b.b_id = ? ORDER BY b.b_id, m2m_cost_price";
    private static final String SELECT_ALL_BOOKINGS = "SELECT b.b_id, b.b_price, b.b_date, b.b_ticket_ti_id, ti.ti_id, ti.ti_description, tr.tr_id, tr.tr_name, m2m_ts.m2m_cost_price, st.st_id, st.st_name, DATE_ADD(d.d_datetime, INTERVAL m2m_ts.m2m_cost_time MINUTE) as m2m_cost_time "
	    + "FROM booking as b JOIN person as p ON p.p_id = b.b_person_p_id JOIN ticket as ti ON b.b_ticket_ti_id = ti.ti_id JOIN "
	    + "m2m_ticket_train_station as m2m_tts ON ti.ti_id = m2m_tts.m2m_ticket_ti_id JOIN m2m_train_station as m2m_ts ON m2m_tts.m2m_m2m_train_station_id = m2m_ts.m2m_train_station_id "
	    + "JOIN train as tr ON tr.tr_id = m2m_ts.m2m_train_tr_id JOIN departure as d ON d.d_train_tr_id = tr.tr_id JOIN station as st "
	    + "ON m2m_ts.m2m_station_st_id = st.st_id ORDER BY b.b_id, m2m_cost_price";
    private static final String SELECT_ALL_BOOKINGS_BY_PERSON_ID = "SELECT b.b_id, b.b_price, b.b_date, b.b_ticket_ti_id, ti.ti_id, ti.ti_description, tr.tr_id, tr.tr_name, m2m_ts.m2m_cost_price, st.st_id, st.st_name, DATE_ADD(d.d_datetime, INTERVAL m2m_ts.m2m_cost_time MINUTE) as m2m_cost_time "
	    + "FROM booking as b JOIN person as p ON p.p_id = b.b_person_p_id JOIN ticket as ti ON b.b_ticket_ti_id = ti.ti_id JOIN "
	    + "m2m_ticket_train_station as m2m_tts ON ti.ti_id = m2m_tts.m2m_ticket_ti_id JOIN m2m_train_station as m2m_ts ON m2m_tts.m2m_m2m_train_station_id = m2m_ts.m2m_train_station_id "
	    + "JOIN train as tr ON tr.tr_id = m2m_ts.m2m_train_tr_id JOIN departure as d ON d.d_train_tr_id = tr.tr_id JOIN station as st "
	    + "ON m2m_ts.m2m_station_st_id = st.st_id" + " WHERE p.p_id = ? ORDER BY b.b_id, m2m_cost_price";
    private static final String CREATE_BOOKING = "INSERT INTO booking (b_price, b_date, b_person_p_id, b_ticket_ti_id) VALUES (?,?,?,?)";
    private static final String UPDATE_BOOKING = "UPDATE booking SET b_price = ?, b_date = ? WHERE b_id = ?";
    private static final String DELETE_BOOKING_BY_ID = "DELETE FROM booking WHERE b_id = ?";

    private static final Logger LOGGER = Logger.getLogger(JdbcBookingDao.class);
    private Connection connection;

    public JdbcBookingDao(Connection connection) {
	this.connection = connection;
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
	    String errorMessage = LogMessage.DB_ERROR_FIND_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return result;
    }

    private Booking getBookingFromResultSet(ResultSet rs) {
	Booking booking;
	try {
	    booking = new Booking.Builder().withId(rs.getInt("b_id")).withPrice(rs.getBigDecimal("b_price"))
		    .withDate(rs.getTimestamp("b_date").toLocalDateTime()).withTicket(getTicketFromResultSet(rs))
		    .build();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Booking.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return booking;
    }

    private Ticket getTicketFromResultSet(ResultSet rs) {
	Ticket ticket = null;
	try {
	    if (rs.getInt("ti_id") != 0) {
		ticket = new Ticket.Builder().withId(rs.getInt("ti_id")).withDescription(rs.getString("ti_description"))
			.withTrainStationList(getStationsFromResultSet(rs)).build();
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Ticket.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return ticket;
    }

    private List<TrainStation> getStationsFromResultSet(ResultSet rs) {
	List<TrainStation> trainStationList = new ArrayList<>();
	try {
	    int bookingId = rs.getInt("b_id");
	    while (rs.getInt("b_id") == bookingId) {
		TrainStation trainStation = new TrainStation.Builder().withPrice(rs.getBigDecimal("m2m_cost_price"))
			.withDateTime(rs.getTimestamp("m2m_cost_time").toLocalDateTime())
			.withStation(getStationFromResultSet(rs)).withTrain(getTrainFromResultSet(rs)).build();
		trainStationList.add(trainStation);
		if (rs.next() == false || rs.getInt("b_id") != bookingId) {
		    rs.previous();
		    return trainStationList;
		}
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + TrainStation.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return trainStationList;
    }

    private Train getTrainFromResultSet(ResultSet rs) {
	Train train = null;
	try {
	    if (rs.getInt("tr_id") != 0) {
		train = new Train.Builder().withId(rs.getInt("tr_id")).withName(rs.getString("tr_name")).build();
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Train.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return train;
    }

    private Station getStationFromResultSet(ResultSet rs) {
	Station station = null;
	try {
	    if (rs.getInt("st_id") != 0) {
		station = new Station.Builder().withId(rs.getInt("st_id")).withName(rs.getString("st_name")).build();
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Station.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return station;
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
	    String errorMessage = LogMessage.DB_ERROR_RETRIEVES_ENTITY + Booking.class.getName();
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return bookingList;
    }

    @Override
    public List<Booking> findAll() {
	List<Booking> bookingList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_BOOKINGS);
		ResultSet rs = query.executeQuery();) {
	    Booking booking = null;
	    while (rs.next()) {
		booking = getBookingFromResultSet(rs);
		bookingList.add(booking);
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_FIND_ALL;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
	return bookingList;
    }

    @Override
    public void create(Booking booking) {
	try (PreparedStatement query = connection.prepareStatement(CREATE_BOOKING, Statement.RETURN_GENERATED_KEYS)) {
	    query.setBigDecimal(1, booking.getPrice());
	    query.setTimestamp(2, Timestamp.valueOf(booking.getDate()));
	    query.setInt(3, booking.getUser().getId());
	    query.setInt(4, booking.getTicket().getId());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		booking.setId(keys.getInt(1));
	    }
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_CREATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
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
	    String errorMessage = LogMessage.DB_ERROR_UPDATE;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }

    @Override
    public void delete(int id) {
	try (PreparedStatement query = connection.prepareStatement(DELETE_BOOKING_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	} catch (SQLException ex) {
	    String errorMessage = LogMessage.DB_ERROR_DELETE_BY_ID;
	    LOGGER.error(errorMessage, ex);
	    throw new RuntimeException(errorMessage, ex);
	}
    }
}
