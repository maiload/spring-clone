package org.example.aop;

import org.example.di.BeanFactory;
import org.example.mvc.controller.Controller;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import static org.example.aop.Aop.AFTER;
import static org.example.aop.Aop.BEFORE;


public class AspectHandler {
    public static final Map<Class<?>, Controller> proxies = new HashMap<>();

    public void addProxy(Class<?> clazz, Advice advice, Aop aop) {
        Controller proxyController = (Controller) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{Controller.class}, (proxy, method, args) -> {
                    if(aop == BEFORE){
                        advice.execute();
                    }
                    Object result = method.invoke(BeanFactory.getBean(clazz), args);
                    if(aop == AFTER){
                        advice.execute();
                    }
                    return result;
                });
        proxies.put(clazz, proxyController);
    }
}
