package org.example.mvc;

import org.example.mvc.controller.AnnotationController;
import org.example.mvc.controller.Controller;

import java.util.HashMap;
import java.util.Map;

public class AnnotationHandlerMapper implements HandlerMapper{
    private final Map<HandlerKey, AnnotationController> handlers;

    public AnnotationHandlerMapper() {
        this.handlers = new HashMap<>();
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return null;
    }

}
