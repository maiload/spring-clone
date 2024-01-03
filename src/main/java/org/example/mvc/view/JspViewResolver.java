package org.example.mvc.view;

import static org.example.mvc.view.JspRedirectView.DEFAULT_REDIRECT_PREFIX;

public class JspViewResolver{
    public View resolveView(String viewName) {
        if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
            return new JspRedirectView(viewName);
        }

        return new JspView(viewName);
    }
}
