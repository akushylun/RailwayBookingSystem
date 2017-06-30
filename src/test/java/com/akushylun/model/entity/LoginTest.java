package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.akushylun.model.entities.Login;

public class LoginTest {
    @Test
    public void shouldCreateLogin() {
	final int ID = 1;
	final String LOGIN = "John";
	final String PASSWORD = "12345John";

	Login userLogin = new Login.Builder().withId(ID).withLogin(LOGIN).withPassword(PASSWORD).build();

	assertNotNull(userLogin);
	assertNotNull(userLogin.toString());
	assertTrue(userLogin.equals(userLogin));
	assertEquals(ID, userLogin.getId());
	assertEquals(LOGIN, userLogin.getLogin());
	assertEquals(PASSWORD, userLogin.getPassword());
    }
}
