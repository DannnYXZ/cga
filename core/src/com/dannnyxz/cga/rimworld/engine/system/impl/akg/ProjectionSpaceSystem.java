package com.dannnyxz.cga.rimworld.engine.system.impl.akg;

import com.badlogic.gdx.math.Matrix4;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.akg.VertexComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;

import java.util.Collection;
import java.util.Optional;

import static com.badlogic.gdx.math.Matrix4.*;

public class ProjectionSpaceSystem extends System {
    @Override
    public void processEntities(long tick) {
        float width = 1920;
        float height = 1080;
        float zNear = 0;
        float zFar = 5;

        Matrix4 m = new Matrix4();

        /*m.val[M00] = 2 / width;
        m.val[M11] = 2 / height;
        m.val[M22] = 1 / (zNear - zFar);
        m.val[M23] = zNear / (zNear - zFar);

        m.tra();*/

        m.val[M00] = 2 / width;
        m.val[M11] = 2 / height;
        m.val[M22] = 1 / (zNear - zFar);
        m.val[M32] = zNear / (zNear - zFar);

        Collection<Entity> entities = ComponentUtils.getEntitiesMatchingComponent(entityManager.getEntities(), VertexComponent.class);
        for (Entity entity : entities) {
            Optional<Component> componentOptional = ComponentUtils.findFirstComponent(entity, VertexComponent.class);
            if (componentOptional.isPresent()) {
                VertexComponent vertexComponent = (VertexComponent) componentOptional.get();
                vertexComponent.getD().mul(m);
                java.lang.System.out.println(vertexComponent.getD().toString());
//                break;
            }
        }
    }
}
