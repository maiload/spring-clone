package org.example.mvc.controller;

import org.example.mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller{
    private static final Logger log = LoggerFactory.getLogger(ForwardController.class);
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("[ForwardController] handleRequest");
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        modelAndView.addObject("name", "홍길동");
        return modelAndView;
    }
}
