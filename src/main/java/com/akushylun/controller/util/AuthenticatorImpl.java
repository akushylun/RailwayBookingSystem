package com.akushylun.controller.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.akushylun.model.entities.Person;

public class AuthenticatorImpl implements Authenticator {

    private static final String AUTH_TOKEN = "authToken";
    private HttpServletRequest request;

    public AuthenticatorImpl(HttpServletRequest request) {
	this.request = request;
    }

    @Override
    public Person getLoggedPerson() {
	Person person = null;
	if (isLoggedIn()) {
	    person = (Person) request.getSession(true).getAttribute(AUTH_TOKEN);
	}
	return person;
    }

    @Override
    public boolean isLoggedIn() {
	boolean isLoggedIn = false;
	HttpSession session = request.getSession(false);
	if (session != null) {
	    isLoggedIn = session.getAttribute(AUTH_TOKEN) != null;
	}
	return isLoggedIn;
    }

    @Override
    public void logout() {
	HttpSession session = request.getSession(false);
	if (session != null) {
	    session.setAttribute(AUTH_TOKEN, null);
	    session.invalidate();
	}
    }

    @Override
    public void setAttributeToSession(HttpSession session, Person person) {
	session.setAttribute(AUTH_TOKEN, person);
    }

}
