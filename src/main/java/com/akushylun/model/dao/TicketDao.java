package com.akushylun.model.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.akushylun.model.entities.Ticket;

public interface TicketDao extends GenericDao<Ticket> {

    List<Ticket> findAll(int bookingId) throws SQLException;

    List<Ticket> findAll(String stationStart, String stationEnd, LocalDate startDate) throws SQLException;

}
