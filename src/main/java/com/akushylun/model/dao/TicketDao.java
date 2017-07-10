package com.akushylun.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.akushylun.model.entities.Ticket;

public interface TicketDao extends GenericDao<Ticket> {

    List<Ticket> findAll(int bookingId) throws SQLException;

}
