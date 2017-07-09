package com.akushylun.controller.security;

import javax.servlet.http.HttpSession;

import com.akushylun.model.entities.Person;

/**
 * Basic security interface
 */
public interface Authenticator {

    /**
     * Gets the logged person
     * 
     * @return Person, the logged Person
     */
    Person getLoggedPerson();

    /**
     * Set attribute and it's value to request
     * 
     * 
     */
    void setAttributeToSession(HttpSession session, Person person);

    /**
     * Returns the current HttpSession or associated with this request or if the
     * request does not have a session, creates one.
     * 
     * @return the {@code HttpSession}
     */
    HttpSession getSession();

    /**
     * Checks if the current person is logged in
     * 
     * @return true if the person is logged in
     */
    boolean isLoggedIn();

    /**
     * Logs out the person
     */
    void logout();
}
