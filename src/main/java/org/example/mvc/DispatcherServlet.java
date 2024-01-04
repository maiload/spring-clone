package org.example.mvc;

import org.example.aop.AspectHandler;
import org.example.di.BeanFactory;
import org.example.mvc.controller.HomeController;
import org.example.mvc.controller.RequestMethod;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.ModelAndView;
import org.example.mvc.view.ViewResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.example.aop.Aop.AFTER;
import static org.example.aop.Aop.BEFORE;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private List<HandlerMapper> handlerMappers;
    private List<HandlerAdapter> handlerAdapters;
    private List<ViewResolver> viewResolvers;

    @Override
    public void init() throws ServletException {
        log.debug("[DispatcherServlet] init.");
        new BeanFactory();
        addAspect();
        this.handlerMappers = List.of(new InterfaceHandlerMapper(), new AnnotationHandlerMapper("org.example.mvc"));
        this.handlerAdapters = List.of(new InterfaceHandlerAdapter(), new AnnotationHandlerAdapter());
        this.viewResolvers = Collections.singletonList(new JspViewResolver());
    }

    private void addAspect() {
        AspectHandler aspectHandler = new AspectHandler();
        aspectHandler.addProxy(HomeController.class, () -> {
            log.info("Hello! Welcome to visit out site.");
        }, AFTER);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("[DispatcherServlet] service started.");
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

        viewResolvers.stream()
                .map(viewResolver -> viewResolver.resolveView(modelAndView.getViewName()))
                .filter(Objects::nonNull)
                .findFirst()
                .ifPresent(view -> {
                    try {
//                        log.debug("VIEW : {}", view);
                        view.render(request, response, modelAndView.getModel());
                    } catch (Exception e) {
                        log.error("exception occurred: [{}]", e.getMessage(), e);
                    }
                });
    }
}
