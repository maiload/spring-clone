package org.example.mvc.controller;

import org.example.mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectController implements Controller{
    private static final Logger log = LoggerFactory.getLogger(RedirectController.class);
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("[ForwardController] handleRequest");
        return new ModelAndView("redirect:/");
    }
}
