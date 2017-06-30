package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.akushylun.model.entity.Person.Role;

public class PersonTest {

    final int ID = 1;
    final Login LOGIN = new Login.Builder().build();
    final String NAME = "John";
    final String SURNAME = "Doe";
    final String EMAIL = "john@gmail.com";
    final Role USER = Role.USER;
    Booking booking = new Booking.Builder().build();
    List<Booking> ordersList = new ArrayList<>();

    @Test
    public void shouldCreateUserBuilder() {
	ordersList.add(booking);
	Person person = new Person.Builder().withId(ID).withPersonLogin(LOGIN).withName(NAME).withSurname(SURNAME)
		.withEmail(EMAIL).withOrders(ordersList).withRole(USER.name()).build();

	assertNotNull(person);
	assertNotNull(person.toString());
	assertTrue(person.equals(person));
	assertEquals(ID, person.getId());
	assertEquals(LOGIN, person.getLogin());
	assertEquals(NAME, person.getName());
	assertEquals(SURNAME, person.getSurname());
	assertEquals(EMAIL, person.getEmail());
	assertEquals(ordersList, person.getOrders());
	assertEquals(USER.name(), person.getRole());
    }

}
