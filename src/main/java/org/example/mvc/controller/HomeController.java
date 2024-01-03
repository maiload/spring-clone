package org.example.mvc.controller;

import org.example.mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller{
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("[HomeController] handleRequest");
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("home", "집");
        return modelAndView;
    }
}
