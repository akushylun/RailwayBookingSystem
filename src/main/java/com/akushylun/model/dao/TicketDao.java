package com.akushylun.model.dao;

import java.util.List;

import com.akushylun.model.entities.Ticket;

public interface TicketDao extends GenericDao<Ticket> {

    List<Ticket> findAll(int bookingId);

    void createM2MTicketTrainStationTo(Ticket ticket);

}
