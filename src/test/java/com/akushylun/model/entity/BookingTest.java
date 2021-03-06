package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @Test
    public void shouldCreateOrder() {

	Booking booking = new Booking.Builder().withId(ID).withPrice(PRICE).withDate(DATE).withTicket(ticket)
		.withUser(person).build();

	assertNotNull(booking);
	assertNotNull(booking.toString());
	assertTrue(booking.equals(booking));
	assertEquals(ID, booking.getId());
	assertEquals(PRICE, booking.getPrice());
	assertEquals(DATE, booking.getDate());
	assertEquals(person, booking.getUser());

    }
}
