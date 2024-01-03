package org.example.mvc.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class JspRedirectView implements View {
    public static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
    private final String uriPath;

    public JspRedirectView(String uriPath) {
        this.uriPath = uriPath;
    }


    @Override
    public void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws ServletException, IOException {
        response.sendRedirect(uriPath.substring(DEFAULT_REDIRECT_PREFIX.length()));
    }

    @Override
    public String toString() {
        return "JspRedirectView{" +
                "uriPath='" + uriPath + '\'' +
                '}';
    }
}
