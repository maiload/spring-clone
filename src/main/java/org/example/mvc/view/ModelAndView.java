package org.example.mvc.view;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ModelAndView {
    private String viewName;
    private final Map<String, Object> model;

    public ModelAndView(String viewName) {
        this();
        this.viewName = viewName;
    }

    public ModelAndView() {
        this.model = new HashMap<>();
    }

    public void addObject(String key, Object value){
        model.put(key, value);
    }

    public Map<String, Object> getModel() {
        return Collections.unmodifiableMap(model);
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
