package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.akushylun.model.entities.Booking;
import com.akushylun.model.entities.Login;
import com.akushylun.model.entities.Person;
import com.akushylun.model.entities.Person.Role;

public class PersonTest {

    private final static int ID = 1;
    private final static Login LOGIN = new Login.Builder().build();
    private final static String NAME = "John";
    private final static String SURNAME = "Doe";
    private final static Role USER = Role.USER;
    private Booking booking = new Booking.Builder().build();
    private List<Booking> ordersList = new ArrayList<>();

    @Test
    public void shouldCreateUserBuilder() {
	ordersList.add(booking);
	Person person = new Person.Builder().withId(ID).withPersonLogin(LOGIN).withName(NAME).withSurname(SURNAME)
		.withOrders(ordersList).withRole(USER).build();

	assertNotNull(person);
	assertNotNull(person.toString());
	assertTrue(person.equals(person));
	assertEquals(ID, person.getId());
	assertEquals(LOGIN, person.getLogin());
	assertEquals(NAME, person.getName());
	assertEquals(SURNAME, person.getSurname());
	assertEquals(ordersList, person.getOrders());
	assertEquals(USER, person.getRole());
    }

}
