package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.akushylun.model.entities.Departure;
import com.akushylun.model.entities.Station;
import com.akushylun.model.entities.Train;

public class TrainTest {

    private final static int ID = 1;
    private final static String NAME = "Hyunday";
    private List<Station> stationList = new ArrayList<Station>();
    private List<Departure> departureList = new ArrayList<Departure>();;

    @Test
    public void shouldCreateTrain() {
	Train train = new Train.Builder().withId(ID).withName(NAME).withStationList(stationList)
		.withDepartureList(departureList).build();

	assertNotNull(train);
	assertNotNull(train.toString());
	assertTrue(train.equals(train));
	assertEquals(ID, train.getId());
	assertEquals(NAME, train.getName());
	assertEquals(stationList, train.getStationList());
	assertEquals(departureList, train.getDepartureList());
    }
}
