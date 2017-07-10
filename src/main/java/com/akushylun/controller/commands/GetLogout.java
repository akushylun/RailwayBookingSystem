package com.akushylun.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.security.Authenticator;
import com.akushylun.controller.security.AuthenticatorImpl;

public class GetLogout implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	Authenticator authenticator = new AuthenticatorImpl(request);
	authenticator.logout();

	String pageToGo = "";
	pageToGo = "index.jsp";

	return pageToGo;

    }
}
