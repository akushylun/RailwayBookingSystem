package com.akushylun.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.akushylun.model.entities.Booking;

public interface BookingDao extends GenericDao<Booking> {

    void createBookingTicketsLink(Booking booking) throws SQLException;

    void deleteBookingTicketsLink(int id) throws SQLException;

    List<Booking> findAllByUserId(int userId) throws SQLException;

}
