package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StationTest {

    final int ID = 1;
    final String START_STATION = "Kyiv";
    final String END_STATION = "ODESSA";
    Shedule shedule = new Shedule.Builder().build();
    List<Shedule> shedulesList = new ArrayList<>();

    @Test
    public void shouldCreateStationBuilder() {

	shedulesList.add(shedule);
	Station station = new Station.Builder().withId(ID).from(START_STATION).to(END_STATION)
		.withShedules(shedulesList).build();
	assertNotNull(station);
	assertNotNull(station.toString());
	assertTrue(station.equals(station));
	assertEquals(ID, station.getId());
	assertEquals(START_STATION, station.getFrom());
	assertEquals(END_STATION, station.getTo());
	assertEquals(shedulesList, station.getShedules());
    }
}
