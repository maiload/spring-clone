package org.example.aop;

import org.example.di.BeanFactory;
import org.example.mvc.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class AspectHandler {
    private static final Logger log = LoggerFactory.getLogger(AspectHandler.class);
    public static final Map<Class<?>, Controller> proxies = new HashMap<>();

    public void addProxy(Class<?> clazz, Advice advice) {
        Controller proxyController = (Controller) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{Controller.class}, (proxy, method, args) -> {
                    advice.doBeforeMethod();
                    return method.invoke(BeanFactory.getBean(clazz), args);
                });
        proxies.put(clazz, proxyController);
    }
}
