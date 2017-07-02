package com.akushylun.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;

public class GetLogin implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	String pageToGo;
	Authenticator authenticator = new AuthenticatorImpl(request);

	if (authenticator.isLoggedIn()) {
	    pageToGo = "/WEB-INF/view/booking.jsp";
	} else
	    pageToGo = "/WEB-INF/view/login.jsp";
	return pageToGo;

    }
}
