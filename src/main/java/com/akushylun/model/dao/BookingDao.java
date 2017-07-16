package com.akushylun.model.dao;

import java.util.List;

import com.akushylun.model.entities.Booking;

public interface BookingDao extends GenericDao<Booking> {

    void createBookingTicketsLink(Booking booking);

    void deleteBookingTicketsLink(int id);

    List<Booking> findAllByUserId(int userId);

}
