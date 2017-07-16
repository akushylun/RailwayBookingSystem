package com.akushylun.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.util.Authenticator;
import com.akushylun.controller.util.AuthenticatorImpl;
import com.akushylun.controller.util.PagePath;

public class GetLogout implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	Authenticator authenticator = new AuthenticatorImpl(request);
	authenticator.logout();

	return PagePath.INDEX;

    }
}
