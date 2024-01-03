package org.example.mvc.view;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public interface View {
    void render(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws ServletException, IOException;

}
