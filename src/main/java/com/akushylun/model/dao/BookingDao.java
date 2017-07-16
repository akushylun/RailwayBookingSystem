package com.akushylun.model.dao;

import java.util.List;

import com.akushylun.model.entities.Booking;

public interface BookingDao extends GenericDao<Booking> {

    List<Booking> findAllByUserId(int userId);

}
