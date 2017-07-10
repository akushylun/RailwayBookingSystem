package com.akushylun.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.controller.constants.PagePath;

public class GetRegistration implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

	return PagePath.REGISTRATION;

    }
}
