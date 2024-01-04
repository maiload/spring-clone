package org.example.mvc.controller;

import org.example.mvc.annotation.Autowired;
import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.example.mvc.model.User;
import org.example.mvc.repository.UserRepository;
import org.example.mvc.view.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/user/form", method = RequestMethod.GET)
    public ModelAndView createUser(){
        log.info("[UserController] createUser");
        return new ModelAndView("/user/form");
    }
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView userList(HttpServletRequest request, HttpServletResponse response){
        log.info("[UserController] userList");
        request.setAttribute("users", userRepository.findAll());
        return new ModelAndView("user/list");
    }
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView afterCreate(HttpServletRequest request, HttpServletResponse response){
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        log.info("[UserController] registerUser : {}", userId);
        userRepository.save(new User(userId, name));
        return new ModelAndView("redirect:/users");
    }
}
