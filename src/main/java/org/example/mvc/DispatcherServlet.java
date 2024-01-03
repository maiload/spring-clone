package org.example.mvc;

import org.example.mvc.controller.Controller;
import org.example.mvc.view.JspViewResolver;
import org.example.mvc.view.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class DispatcherServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(DispatcherServlet.class);
    private HandlerMapper handlerMapper;
    private JspViewResolver jspViewResolver;

    @Override
    public void init() throws ServletException {
        this.handlerMapper = new HandlerMapper();
        this.jspViewResolver = new JspViewResolver();
        log.info("[DispatcherServlet] initialized.");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/favicon.ico".equals(request.getRequestURI())) {
            log.debug("Ignoring favicon.ico request.");
            return;
        }
        log.info("[DispatcherServlet] service started.");

        Controller handler = handlerMapper.findHandler(request.getRequestURI());
        String viewName = handler.handleRequest(request, response);

        View view = jspViewResolver.resolveView(viewName);
        view.render(request, response);
    }
}
