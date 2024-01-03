package org.example.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller{
    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:/";
    }
}
