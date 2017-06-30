package com.akushylun.model.dao;

import com.akushylun.model.entity.Booking;

public interface BookingDao extends GenericDao<Booking>, AutoCloseable {

    void createBookingTicketsLink(Booking booking);

}
