package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.ForwardController;
import org.example.mvc.controller.HomeController;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapper {
    private final Map<String, Controller> handlers;

    public HandlerMapper() {
        this.handlers = new HashMap<>();
        init();
    }

    private void init() {
        handlers.put("/", new HomeController());
        handlers.put("/forward", new ForwardController());
    }

    public Controller findHandler(String uriPath){
        return handlers.get(uriPath);
    }
}
