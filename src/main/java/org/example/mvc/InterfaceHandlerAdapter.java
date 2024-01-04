package org.example.mvc;

import org.example.aop.AspectHandler;
import org.example.mvc.controller.Controller;
import org.example.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class InterfaceHandlerAdapter implements HandlerAdapter{

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Controller proxyController = AspectHandler.proxies.get(handler.getClass());
        if(Objects.nonNull(proxyController)){
            return (ModelAndView) proxyController.handleRequest(request, response);
        }

        Object result = ((Controller)handler).handleRequest(request, response);
        if (result instanceof String) {
            return new ModelAndView(String.valueOf(result));
        }
        return (ModelAndView) result;
    }

    @Override
    public boolean support(Object handler) {
        return (handler instanceof Controller);
    }
}
