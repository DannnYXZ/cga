package com.dannnyxz.cga.rimworld.engine.system.impl.akg;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.akg.VertexComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;

import java.util.Collection;
import java.util.Optional;

import static com.badlogic.gdx.math.Matrix4.*;

public class ViewportSpaceSystem extends System {
    @Override
    public void processEntities(long tick) {
        float width = 1920;
        float height = 1080;
        float xmin = 0;
        float ymin = 0;

        Matrix4 m = new Matrix4();

        /*m.val[M00] = width / 2;
        m.val[M03] = xmin + width / 2;

        m.val[M11] = -height / 2;
        m.val[M13] = ymin + height / 2;

        m.tra();*/

        m.val[M00] = width / 2;
        m.val[M30] = xmin + width / 2;

        m.val[M11] = -height / 2;
        m.val[M31] = ymin + height / 2;

        Collection<Entity> entities = ComponentUtils.getEntitiesMatchingComponent(entityManager.getEntities(), VertexComponent.class);
        for (Entity entity : entities) {
            Optional<Component> componentOptional = ComponentUtils.findFirstComponent(entity, VertexComponent.class);
            if (componentOptional.isPresent()) {
                VertexComponent vertexComponent = (VertexComponent) componentOptional.get();
                vertexComponent.getD().mul(m);
                java.lang.System.out.println(vertexComponent.getD().toString());
                //java.lang.System.exit(-1);
            }
        }
    }
}
