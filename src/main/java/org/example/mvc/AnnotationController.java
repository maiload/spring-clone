package org.example.mvc;

import org.example.di.BeanFactory;
import org.example.mvc.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AnnotationController {
    private final Class<?> clazz;
    private final Method method;

    public AnnotationController(Class<?> clazz, Method method) {
        this.clazz = clazz;
        this.method = method;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 생성하는 부분 -> beanFactory 로 이동
//        Constructor<?> constructor = clazz.getDeclaredConstructor();
//        Object handler = constructor.newInstance();

        Object handler = BeanFactory.getBean(clazz);

        int parameterCount = method.getParameterCount();
        Object result;
        if (parameterCount == 2) {
            result = method.invoke(handler, request, response);
        }else{
            result = method.invoke(handler);
        }
        if (result instanceof String) {
            return new ModelAndView(String.valueOf(result));
        }
        return (ModelAndView) result;
    }
}
