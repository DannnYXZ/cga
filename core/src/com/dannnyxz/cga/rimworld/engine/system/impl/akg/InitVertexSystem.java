package com.dannnyxz.cga.rimworld.engine.system.impl.akg;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.akg.VertexComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;

import java.util.Collection;
import java.util.Optional;

public class InitVertexSystem extends System {
    @Override
    public void processEntities(long tick) {
        Collection<Entity> entities = ComponentUtils.getEntitiesMatchingComponent(entityManager.getEntities(), VertexComponent.class);
        for (Entity entity : entities) {
            Optional<Component> componentOptional = ComponentUtils.findFirstComponent(entity, VertexComponent.class);
            if (componentOptional.isPresent()) {
                VertexComponent vertexComponent = (VertexComponent) componentOptional.get();
                vertexComponent.setD(vertexComponent.getV().cpy());
                java.lang.System.out.println(vertexComponent.getD().toString());
//                break;
            }
        }
    }
}
