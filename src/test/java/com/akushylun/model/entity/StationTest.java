package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.akushylun.model.entities.Station;

public class StationTest {

    private final static int ID = 1;
    private final static String NAME = "Kyiv";

    @Test
    public void shouldCreateStationBuilder() {

	Station station = new Station.Builder().withId(ID).withName(NAME).build();
	assertNotNull(station);
	assertNotNull(station.toString());
	assertTrue(station.equals(station));
	assertEquals(ID, station.getId());
	assertEquals(NAME, station.getName());

    }
}
