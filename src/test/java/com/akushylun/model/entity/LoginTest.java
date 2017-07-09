package com.akushylun.model.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.akushylun.model.entities.Login;

public class LoginTest {

    private final static int ID = 1;
    private final static String LOGIN = "John";
    private final static String PASSWORD = "12345John";

    @Test
    public void shouldCreateLogin() {

	Login userLogin = new Login.Builder().withId(ID).withEmail(LOGIN).withPassword(PASSWORD).build();

	assertNotNull(userLogin);
	assertNotNull(userLogin.toString());
	assertTrue(userLogin.equals(userLogin));
	assertEquals(ID, userLogin.getId());
	assertEquals(LOGIN, userLogin.getEmail());
	assertEquals(PASSWORD, userLogin.getPassword());

    }
}
