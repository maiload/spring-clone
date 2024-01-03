package org.example.mvc.controller;

import org.example.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    Object handleRequest(HttpServletRequest request, HttpServletResponse response);
}
