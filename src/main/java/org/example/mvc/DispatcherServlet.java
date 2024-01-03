package org.example.mvc;

import org.example.mvc.controller.Controller;
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
    private HandlerAdapter handlerAdapter;

    @Override
    public void init() throws ServletException {
        this.handlerMappers = List.of(new InterfaceHandlerMapper(), new AnnotationHandlerMapper());
        this.jspViewResolver = new JspViewResolver();
        this.handlerAdapter = new HandlerAdapter();
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

        ModelAndView modelAndView = handlerAdapter.handle(request, response, handler);

        View view = jspViewResolver.resolveView(modelAndView.getViewName());
        view.render(request, response, modelAndView.getModel());
    }
}
