package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HandlerAdapter {

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object result = ((Controller)handler).handleRequest(request, response);
        if (result instanceof String) {
            return new ModelAndView(String.valueOf(result));
        }
        return (ModelAndView) result;
    }
}
