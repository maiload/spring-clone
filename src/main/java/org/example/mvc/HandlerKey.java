package org.example.mvc;

import org.example.mvc.controller.RequestMethod;

import java.util.Objects;

public class HandlerKey {
    private final RequestMethod method;
    private final String value; // uriPath

    public HandlerKey(RequestMethod method, String value) {
        this.method = method;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HandlerKey that = (HandlerKey) o;
        return method == that.method && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, value);
    }
}
