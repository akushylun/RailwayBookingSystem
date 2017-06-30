package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Shedule;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;

public class TicketTest {

    final int ID = 1;
    final BigDecimal PRICE = BigDecimal.valueOf(100).setScale(2, BigDecimal.ROUND_HALF_UP);
    Train train = new Train.Builder().build();
    Station station = new Station.Builder().build();
    Shedule shedule = new Shedule.Builder().build();
    Booking booking = new Booking.Builder().build();
    List<Booking> ordersList = new ArrayList<>();

    @Test
    public void shouldCreateTicketBuilder() {

	ordersList.add(booking);
	Ticket ticket = new Ticket.Builder().withId(ID).withPrice(PRICE).withOrders(ordersList).withShedule(shedule)
		.build();

	assertNotNull(ticket);
	assertNotNull(ticket.toString());
	assertTrue(ticket.equals(ticket));
	assertEquals(ID, ticket.getId());
	assertEquals(PRICE, ticket.getPrice());
	assertEquals(ordersList, ticket.getOrders());
	assertEquals(shedule, ticket.getShedule());
    }
}
