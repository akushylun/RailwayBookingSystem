package com.akushylun.model.dao;

import java.util.List;

import com.akushylun.model.entities.Booking;

public interface BookingDao extends GenericDao<Booking>, AutoCloseable {

    void createBookingTicketsLink(Booking booking);

    List<Booking> findAllByUserId(int userId);

}
