package com.akushylun.model.dao;

import java.util.List;

import com.akushylun.model.entities.Ticket;

public interface TicketDao extends GenericDao<Ticket>, AutoCloseable {

    List<Ticket> findAllByBookingId(int id);

}
