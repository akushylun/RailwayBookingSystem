package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TrainTest {

    final int ID = 1;
    final String NAME = "Hyunday";
    Shedule shedule = new Shedule.Builder().build();
    List<Shedule> sheduleList = new ArrayList<>();

    @Test
    public void shouldCreateTrain() {
	Train train = new Train.Builder().withId(ID).withName(NAME).withShedules(sheduleList).build();

	assertNotNull(train);
	assertNotNull(train.toString());
	assertTrue(train.equals(train));
	assertEquals(ID, train.getId());
	assertEquals(NAME, train.getName());
	assertEquals(sheduleList, train.getTickets());
    }
}
