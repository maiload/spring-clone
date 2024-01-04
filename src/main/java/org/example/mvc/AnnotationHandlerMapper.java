package org.example.mvc;

import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AnnotationHandlerMapper implements HandlerMapper{
    private final Map<HandlerKey, AnnotationController> handlers;
    private final Object[] basePackage;

    public AnnotationHandlerMapper(Object... basePackage) {
        this.handlers = new HashMap<>();
        this.basePackage = basePackage;
        init();
    }

    @Override
    public void init() {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> clazzesWithControllerAnnotation = reflections.getTypesAnnotatedWith(Controller.class);
        clazzesWithControllerAnnotation.forEach(clazz -> {
            getMethodWithRequestMapping(clazz).forEach(method -> {  // getDeclaredMethods() 를 하면 생성자도 가져오기 때문에 에러 발생
                RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                handlers.put(new HandlerKey(requestMapping.method(), requestMapping.value()), new AnnotationController(clazz, method));
            });
        });
    }

    private Set<Method> getMethodWithRequestMapping(Class<?> clazz) {
        return ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }

}
