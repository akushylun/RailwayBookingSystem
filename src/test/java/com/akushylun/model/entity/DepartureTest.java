package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;

import com.akushylun.model.entities.Departure;
import com.akushylun.model.entities.Train;

public class DepartureTest {

    private final static int ID = 1;
    private final static LocalDateTime DATE = LocalDateTime.now();
    private Train train = new Train.Builder().build();

    @Test
    public void shouldCreateShedule() {

	Departure departure = new Departure.Builder().withId(ID).withDateTtime(DATE).withTrain(train).build();

	assertNotNull(departure);
	assertNotNull(departure.toString());
	assertTrue(departure.equals(departure));
	assertEquals(ID, departure.getId());
	assertEquals(DATE, departure.getDateTime());
	assertEquals(train, departure.getTrain());

    }
}
