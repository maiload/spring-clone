package org.example.mvc;

public interface HandlerMapper {
    void init();

    Object findHandler(HandlerKey handlerKey);
}
