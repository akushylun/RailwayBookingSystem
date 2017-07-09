package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Ticket;

public class BookingTest {

    private final static int ID = 1;
    private final static BigDecimal PRICE = BigDecimal.valueOf(100.222).setScale(2, BigDecimal.ROUND_HALF_UP);
    private final static LocalDateTime DATE = LocalDateTime.now();
    private Person person = new Person.Builder().build();
    private Ticket ticket = new Ticket.Builder().build();
    private List<Ticket> ticketsList = new ArrayList<>();

    @Test
    public void shouldCreateOrder() {

	ticketsList.add(ticket);
	Booking booking = new Booking.Builder().withId(ID).withPrice(PRICE).withDate(DATE).withTickets(ticketsList)
		.withUser(person).build();

	assertNotNull(booking);
	assertNotNull(booking.toString());
	assertTrue(booking.equals(booking));
	assertEquals(ID, booking.getId());
	assertEquals(PRICE, booking.getPrice());
	assertEquals(DATE, booking.getDate());
	assertEquals(ticketsList, booking.getTickets());
	assertEquals(person, booking.getUser());

    }
}
