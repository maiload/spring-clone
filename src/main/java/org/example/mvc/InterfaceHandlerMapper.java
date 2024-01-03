package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.controller.ForwardController;
import org.example.mvc.controller.HomeController;
import org.example.mvc.controller.RequestMethod;

import java.util.HashMap;
import java.util.Map;

public class InterfaceHandlerMapper implements HandlerMapper{
    private final Map<HandlerKey, Controller> handlers;

    public InterfaceHandlerMapper() {
        this.handlers = new HashMap<>();
        init();
    }

    @Override
    public void init() {   // Reflections 사용하여 자동 등록 안됨 -> 컨트롤러에 매핑 url 정보가 없음
        handlers.put(new HandlerKey(RequestMethod.GET, "/"), new HomeController());
        handlers.put(new HandlerKey(RequestMethod.GET, "/forward"), new ForwardController());
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }
}
