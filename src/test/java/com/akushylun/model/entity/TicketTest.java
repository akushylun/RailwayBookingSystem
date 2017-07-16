package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Ticket;

public class TicketTest {

    private final static int ID = 1;
    private List<Booking> bookingList = new ArrayList<>();

    @Test
    public void shouldCreateTicketBuilder() {

	Ticket ticket = new Ticket.Builder().withId(ID).withBooking(bookingList).build();

	assertNotNull(ticket);
	assertNotNull(ticket.toString());
	assertTrue(ticket.equals(ticket));
	assertEquals(ID, ticket.getId());
	assertEquals(bookingList, ticket.getBookings());

    }
}
