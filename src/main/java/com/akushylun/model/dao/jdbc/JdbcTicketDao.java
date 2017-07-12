package com.akushylun.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.akushylun.model.dao.TicketDao;
import com.akushylun.model.entities.Departure;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;

public class JdbcTicketDao implements TicketDao {

    private static final String SELECT_TICKET_BY_ID = "SELECT ti.ti_id, ti.ti_price, tr.tr_id, tr.tr_name, d.d_id, d.d_datetime, st.st_id,"
	    + " st.st_name, m2m_ts.m2m_cost_time FROM ticket as ti INNER JOIN m2m_booking_ticket as m2m_bt ON ti.ti_id = "
	    + "m2m_bt.m2m_ticket_ti_id JOIN train as tr ON tr.tr_id = ti.ti_train_tr_id INNER JOIN departure as d ON d.d_train_tr_id = "
	    + "tr.tr_id INNER JOIN m2m_train_station as m2m_ts ON m2m_ts.m2m_train_tr_id = tr.tr_id INNER JOIN station as st "
	    + "ON m2m_ts.m2m_station_st_id = st.st_id WHERE ti.ti_id = ? ORDER BY st.st_id;";
    private static final String SELECT_ALL_TICKETS = "SELECT ti.ti_id, ti.ti_price, tr.tr_id, tr.tr_name, d.d_id, "
	    + "d.d_datetime, st.st_id, st.st_name, m2m_ts.m2m_cost_time FROM ticket as ti INNER JOIN m2m_booking_ticket as m2m_bt ON "
	    + "ti.ti_id = m2m_bt.m2m_ticket_ti_id INNER JOIN booking ON booking.b_id = m2m_bt.m2m_booking_b_id JOIN train as tr ON tr.tr_id = "
	    + "ti.ti_train_tr_id INNER JOIN departure as d ON d.d_train_tr_id = tr.tr_id INNER JOIN m2m_train_station as m2m_ts ON "
	    + "m2m_ts.m2m_train_tr_id = tr.tr_id INNER JOIN station as st ON m2m_ts.m2m_station_st_id = st.st_id "
	    + "ORDER BY ti.ti_id, st.st_id";
    private static final String SELECT_ALL_TICKETS_BY_STATION_START_END_DATE = "SELECT A.ti_id, A.ti_price ,A.tr_id, A.tr_name, A.st_name, A.d_id, "
	    + "A.d_datetime, A.m2m_cost_time as cost_time_start, B.m2m_cost_time as cost_time_end, A.st_id as st_id_start, A.st_name as "
	    + "st_name_start,  B.st_id as st_id_end, B.st_name as st_name_end FROM (SELECT ti_id, ti_price, tr_id, tr_name, st_id, st_name, d_id, d_datetime, m2m_cost_time "
	    + "FROM ticket INNER JOIN train ON tr_id = ti_train_tr_id INNER JOIN departure ON tr_id = d_id INNER JOIN m2m_train_station ON tr_id = m2m_train_tr_id INNER JOIN station "
	    + "ON m2m_station_st_id = st_id WHERE st_name = ? AND cast(d_datetime as DATE) = ?) as A INNER JOIN (SELECT ti_id, ti_price, tr_id, tr_name, st_id, st_name, "
	    + "d_id, d_datetime, m2m_cost_time FROM ticket INNER JOIN train ON tr_id = ti_train_tr_id INNER JOIN departure ON tr_id = d_id INNER JOIN m2m_train_station ON tr_id = m2m_train_tr_id "
	    + "INNER JOIN station as s ON m2m_station_st_id = st_id WHERE st_name = ?) as B ON A.tr_id = B.tr_id AND A.d_id = B.d_id";
    private static final String SELECT_ALL_TICKETS_BY_BOOKING_ID = "SELECT ti.ti_id, ti.ti_price, tr.tr_id, tr.tr_name, d.d_id, "
	    + "d.d_datetime, st.st_id, st.st_name, m2m_ts.m2m_cost_time FROM ticket as ti INNER JOIN m2m_booking_ticket as m2m_bt ON "
	    + "ti.ti_id = m2m_bt.m2m_ticket_ti_id INNER JOIN booking ON booking.b_id = m2m_bt.m2m_booking_b_id JOIN train as tr ON tr.tr_id = "
	    + "ti.ti_train_tr_id INNER JOIN departure as d ON d.d_train_tr_id = tr.tr_id INNER JOIN m2m_train_station as m2m_ts ON "
	    + "m2m_ts.m2m_train_tr_id = tr.tr_id INNER JOIN station as st ON m2m_ts.m2m_station_st_id = st.st_id WHERE booking.b_id = ? "
	    + "ORDER BY ti.ti_id, st.st_id";
    private static final String CREATE_TICKET = "INSERT INTO ticket (ti_price, ti_train_tr_id) " + "VALUES (?,?)";
    private static final String UPDATE_TICKET = "UPDATE ticket SET ti_price = ?, ti_train_tr_id = ? WHERE ti_id = ?";
    private static final String DELETE_TICKET_BY_ID = "DELETE FROM ticket WHERE ti_id = ?";

    private Connection connection;

    public JdbcTicketDao(Connection connection) {
	this.connection = connection;
    }

    private Ticket getTicketFromResultSet(ResultSet rs) throws SQLException {
	Ticket ticket = new Ticket.Builder().withId(rs.getInt("ti_id")).withPrice(rs.getBigDecimal("ti_price"))
		.withTrain(getTrainFromResultSet(rs)).build();
	return ticket;
    }

    private Train getTrainFromResultSet(ResultSet rs) throws SQLException {
	Train train = null;
	train = new Train.Builder().withId(rs.getInt("tr_id")).withName(rs.getString("tr_name"))
		.withDepartureList(getDeparturesFromResultSet(rs)).withStationList(getStationsFromResultSet(rs))
		.build();
	return train;
    }

    private List<Departure> getDeparturesFromResultSet(ResultSet rs) throws SQLException {
	List<Departure> departureList = new ArrayList<>();
	Departure departure = null;
	if (rs.getInt("d_id") != 0) {
	    departure = new Departure.Builder().withId(rs.getInt("d_id"))
		    .withDateTtime(rs.getTimestamp("d_datetime").toLocalDateTime()).build();
	    departureList.add(departure);
	}
	return departureList;
    }

    private List<Station> getStationsFromResultSet(ResultSet rs) throws SQLException {
	List<Station> stationList = new ArrayList<>();
	Station station = null;
	int ticketId = rs.getInt("ti_id");
	ResultSet result = rs;
	boolean flag = true;
	while ((flag == true) && containsStation(result, ticketId)) {
	    station = new Station.Builder().withId(result.getInt("st_id")).withName(result.getString("st_name"))
		    .build();
	    stationList.add(station);
	    flag = rs.next();
	}
	return stationList;
    }

    private boolean containsStation(ResultSet rs, int ticketId) throws SQLException {
	boolean contains;
	if (rs.getInt("ti_id") == ticketId) {
	    contains = true;
	} else {
	    contains = false;
	}
	return contains;
    }

    @Override
    public Optional<Ticket> find(int id) throws SQLException {
	Optional<Ticket> result = Optional.empty();
	try (PreparedStatement query = connection.prepareStatement(SELECT_TICKET_BY_ID)) {
	    query.setInt(1, id);
	    ResultSet rs = query.executeQuery();
	    Ticket Ticket;
	    if (rs.next()) {
		Ticket = getTicketFromResultSet(rs);
		result = Optional.of(Ticket);
	    }
	}
	return result;
    }

    @Override
    public List<Ticket> findAll() throws SQLException {
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
	}
	return ticketList;
    }

    @Override
    public List<Ticket> findAll(int bookingId) throws SQLException {
	List<Ticket> ticketList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_TICKETS_BY_BOOKING_ID)) {
	    query.setInt(1, bookingId);
	    ResultSet rs = query.executeQuery();
	    Ticket ticket;
	    while (rs.next()) {
		ticket = getTicketFromResultSet(rs);
		ticketList.add(ticket);
	    }
	}
	return ticketList;
    }

    @Override
    public List<Ticket> findAll(String stationStart, String stationEnd, LocalDate startDate) throws SQLException {
	List<Ticket> ticketList = new ArrayList<>();
	try (PreparedStatement query = connection.prepareStatement(SELECT_ALL_TICKETS_BY_STATION_START_END_DATE)) {
	    query.setString(1, stationStart);
	    query.setString(2, startDate.toString());
	    query.setString(3, stationEnd);
	    ResultSet rs = query.executeQuery();
	    Ticket ticket;
	    while (rs.next()) {
		ticket = extractTicketFromResultSet(rs);
		ticketList.add(ticket);
	    }
	}
	return ticketList;
    }

    private Ticket extractTicketFromResultSet(ResultSet rs) throws SQLException {
	Ticket ticket;
	List<Departure> departureList = new ArrayList<>();
	Departure departure;
	departure = new Departure.Builder().withId(rs.getInt("d_id"))
		.withDateTtime(rs.getTimestamp("d_datetime").toLocalDateTime()).build();
	departureList.add(departure);
	List<Station> stationList = new ArrayList<>();
	Station stationFrom = new Station.Builder().withId(rs.getInt("st_id_start"))
		.withName(rs.getString("st_name_start")).build();
	Station stationTo = new Station.Builder().withId(rs.getInt("st_id_end")).withName(rs.getString("st_name_end"))
		.build();
	stationList.add(stationFrom);
	stationList.add(stationTo);
	Train train = new Train.Builder().withId(rs.getInt("tr_id")).withName(rs.getString("tr_name"))
		.withDepartureList(departureList).withStationList(stationList).build();

	ticket = new Ticket.Builder().withId(rs.getInt("ti_id")).withPrice(rs.getBigDecimal("ti_price"))
		.withTrain(train).build();
	return ticket;
    }

    @Override
    public void create(Ticket ticket) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(CREATE_TICKET, Statement.RETURN_GENERATED_KEYS)) {
	    query.setBigDecimal(1, ticket.getPrice());
	    query.setInt(2, ticket.getTrain().getId());
	    query.executeUpdate();
	    ResultSet keys = query.getGeneratedKeys();
	    if (keys.next()) {
		ticket.setId(keys.getInt(1));
	    }
	}
    }

    @Override
    public void update(Ticket ticket) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(UPDATE_TICKET)) {
	    query.setBigDecimal(1, ticket.getPrice());
	    query.setInt(2, ticket.getTrain().getId());
	    query.setInt(3, ticket.getId());
	    query.executeUpdate();
	}
    }

    @Override
    public void delete(int id) throws SQLException {
	try (PreparedStatement query = connection.prepareStatement(DELETE_TICKET_BY_ID)) {
	    query.setInt(1, id);
	    query.executeUpdate();
	}
    }
}
