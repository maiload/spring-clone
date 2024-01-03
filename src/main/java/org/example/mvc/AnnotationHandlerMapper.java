package org.example.mvc;

import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.RequestMapping;
import org.reflections.Reflections;

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
            Arrays.stream(clazz.getDeclaredMethods()).forEach(method -> {
                RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                handlers.put(new HandlerKey(requestMapping.method(), requestMapping.value()), new AnnotationController(clazz, method));
            });
        });
    }

    @Override
    public Object findHandler(HandlerKey handlerKey) {
        return handlers.get(handlerKey);
    }

}
