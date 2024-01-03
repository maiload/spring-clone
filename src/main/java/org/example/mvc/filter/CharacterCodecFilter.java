package org.example.mvc.filter;

import org.example.mvc.DispatcherServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class CharacterCodecFilter implements Filter {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private final static Logger log = LoggerFactory.getLogger(CharacterCodecFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ("/favicon.ico".equals(((HttpServletRequest)request).getRequestURI())) {
            log.debug("Ignoring favicon.ico request.");
            return;
        }
        request.setCharacterEncoding(DEFAULT_ENCODING);
        response.setCharacterEncoding(DEFAULT_ENCODING);

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
