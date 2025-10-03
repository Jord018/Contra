package org.game.contra.entity;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private final Map<Class<?>, Object> components = new HashMap<>();

    public <T> void addComponent(T component) {
        components.put(component.getClass(), component);
    }

    @SuppressWarnings("unchecked")
    public <T> T getComponent(Class<T> componentClass) {
        return (T) components.get(componentClass);
    }

    public boolean hasComponent(Class<?> componentClass) {
        return components.containsKey(componentClass);
    }
}
