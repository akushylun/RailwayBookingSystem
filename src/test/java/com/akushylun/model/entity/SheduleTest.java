package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.akushylun.model.entities.Shedule;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Ticket;
import com.akushylun.model.entities.Train;

public class SheduleTest {

    final int ID = 1;
    final LocalDateTime START_DATE = LocalDateTime.now();
    final LocalDateTime END_DATE = LocalDateTime.now();
    Ticket ticket = new Ticket.Builder().build();
    List<Ticket> ticketsList = new ArrayList<>();
    Station station = new Station.Builder().build();
    Train train = new Train.Builder().build();

    @Test
    public void shouldCreateShedule() {

	ticketsList.add(ticket);
	Shedule shedule = new Shedule.Builder().withId(ID).start(START_DATE).end(END_DATE).withTickets(ticketsList)
		.withStation(station).withTrain(train).build();
	assertNotNull(shedule);
	assertNotNull(shedule.toString());
	assertTrue(shedule.equals(shedule));
	assertEquals(ID, shedule.getId());
	assertEquals(START_DATE, shedule.getStart());
	assertEquals(END_DATE, shedule.getEnd());
	assertEquals(ticketsList, shedule.getTickets());
	assertEquals(station, shedule.getStation());
	assertEquals(train, shedule.getTrain());
    }
}
