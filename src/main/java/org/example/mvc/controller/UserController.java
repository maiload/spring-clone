package org.example.mvc.controller;

import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/user/form", method = RequestMethod.GET)
    public ModelAndView createUser(){
        log.info("[UserController] createUser");
        return new ModelAndView("/user/form");
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView userList(){
        log.info("[UserController] userList");
        return new ModelAndView("user/list");
    }
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView afterCreate(HttpServletRequest request, HttpServletResponse response){
        log.info("[UserController] registerUser");
        return new ModelAndView("redirect:/users");
    }
}
