package com.akushylun.controller.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.akushylun.model.dao.exceptions.ServiceException;

public interface Command {
    String execute(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException, ServiceException;
}
