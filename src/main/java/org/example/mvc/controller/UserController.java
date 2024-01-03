package org.example.mvc.controller;

import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.view.ModelAndView;

@Controller
public class UserController {
    @RequestMapping(value = "/user/form", method = RequestMethod.GET)
    public ModelAndView createUser(){
        return new ModelAndView("user/form");
    }
}
