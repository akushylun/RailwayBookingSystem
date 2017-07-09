package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;

public class TicketTest {

    private final static int ID = 1;
    private final static BigDecimal PRICE = BigDecimal.valueOf(100).setScale(2, BigDecimal.ROUND_HALF_UP);
    private Train train = new Train.Builder().build();
    private List<Booking> bookingList = new ArrayList<>();

    @Test
    public void shouldCreateTicketBuilder() {

	Ticket ticket = new Ticket.Builder().withId(ID).withPrice(PRICE).withBooking(bookingList).withTrain(train)
		.build();

	assertNotNull(ticket);
	assertNotNull(ticket.toString());
	assertTrue(ticket.equals(ticket));
	assertEquals(ID, ticket.getId());
	assertEquals(PRICE, ticket.getPrice());
	assertEquals(train, ticket.getTrain());
	assertEquals(bookingList, ticket.getBookings());

    }
}
