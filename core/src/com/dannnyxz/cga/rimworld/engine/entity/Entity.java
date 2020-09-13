package com.dannnyxz.cga.rimworld.engine.entity;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Entity {

    private long id;

    private Map<String, Component> components = new HashMap<>();

    public Entity(long id) {
        this.id = id;
    }

    public Entity with(Component component) {
        components.put(component.getClass().getSimpleName(), component);
        return this;
    }
}
