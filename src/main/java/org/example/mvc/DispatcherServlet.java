package org.example.mvc;

import org.example.mvc.controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.ModelAndView;
import org.example.mvc.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private List<HandlerMapper> handlerMappers;
    private JspViewResolver jspViewResolver;
    private List<HandlerAdapter> handlerAdapters;

    @Override
    public void init() throws ServletException {
        this.handlerMappers = List.of(new InterfaceHandlerMapper(), new AnnotationHandlerMapper("org.example.mvc"));
        this.jspViewResolver = new JspViewResolver();
        this.handlerAdapters = List.of(new InterfaceHandlerAdapter(), new AnnotationHandlerAdapter());
        log.info("[DispatcherServlet] initialized.");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("[DispatcherServlet] service started.");
        String requestURI = request.getRequestURI();
        RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());

        Object handler = handlerMappers.stream()
                .map(handlerMapper -> handlerMapper.findHandler(new HandlerKey(requestMethod, requestURI)))
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new ServletException("No handler for [" + requestMethod + "," + requestURI + "]"));

        HandlerAdapter handlerAdapter = handlerAdapters.stream()
                .filter(adapter -> adapter.support(handler))
                .findFirst()
                .orElseThrow(() -> new ServletException("No adapter for handler [" + handler + "]"));

        ModelAndView modelAndView;
        try {
            modelAndView = handlerAdapter.handle(request, response, handler);
        } catch (Exception e) {
            log.error("exception occurred: [{}]", e.getMessage(), e);
            throw new ServletException();
        }

        View view = jspViewResolver.resolveView(modelAndView.getViewName());
        log.info("VIEW : {}", view.toString());
        view.render(request, response, modelAndView.getModel());
    }
}
