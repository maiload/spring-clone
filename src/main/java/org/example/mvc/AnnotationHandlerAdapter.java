package org.example.mvc;

import org.example.mvc.controller.AnnotationController;
import org.example.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnotationHandlerAdapter implements HandlerAdapter{
    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return null;
    }

    @Override
    public boolean support(Object handler) {
        return (handler instanceof AnnotationController);
    }
}
