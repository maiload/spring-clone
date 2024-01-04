package org.example.di;

import org.example.mvc.annotation.Autowired;
import org.example.mvc.annotation.Controller;
import org.example.mvc.annotation.Repository;
import org.example.mvc.controller.UserController;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {
    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);
    private final Set<Class<?>> clazzes;
    private final static Map<Class<?>, Object> beans = new HashMap<>();

    public BeanFactory() {
        this.clazzes = getTypesAnnotatedWith(Controller.class, Repository.class);
        initialize();
    }

    private void initialize() {
        clazzes.forEach(clazz -> {
            Object instance = createInstance(clazz);
            beans.put(clazz, instance);
        });
        log.debug("[BeanFactory] Beans initialized");
    }

    private Object createInstance(Class<?> clazz) {
        Object instance = getBean(clazz);
        if(Objects.nonNull(instance)){
            return instance;
        }

        Constructor<?> constructor = findConstructor(clazz);

        List<Object> parameters = Arrays.stream(constructor.getParameterTypes())
                .map(this::createInstance)
                .peek(o -> log.debug("[{}] parameters : {}", clazz, o))
                .collect(Collectors.toList());

        try {
            if(parameters.isEmpty()){
                return constructor.newInstance();
            }else{
                return constructor.newInstance(parameters.toArray());
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }

    private Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation>... annotations) {
        Set<Class<?>> clazzes = new HashSet<>();
        Reflections reflections = new Reflections("org.example");
        Arrays.stream(annotations).forEach(annotation -> clazzes.addAll(reflections.getTypesAnnotatedWith(annotation)));
        return clazzes;
    }

    private Constructor<?> findConstructor(Class<?> clazz) {
        Constructor<?> constructor = getAutowiredConstructor(clazz);

        if (Objects.nonNull(constructor)) {
            return constructor;
        }

        return clazz.getConstructors()[0];
    }

    private Constructor<?> getAutowiredConstructor(Class<?> clazz) {
        Set<Constructor> autowiredConstructors = ReflectionUtils.getAllConstructors(clazz, ReflectionUtils.withAnnotation(Autowired.class));
        if (autowiredConstructors.isEmpty()) {
            return null;
        }
        return autowiredConstructors.iterator().next();
    }

}
