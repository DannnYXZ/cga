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

public class ViewSpaceSystem extends System {
    @Override
    public void processEntities(long tick) {
        Vector3 eye = new Vector3(0, 0, 1);
        Vector3 target = new Vector3(0, 0, 0);
        Vector3 up = new Vector3(0, 1, 0);

        Vector3 zAxis = eye.cpy().sub(target).nor();
        Vector3 xAxis = up.cpy().crs(zAxis).nor();
        Vector3 yAxis = zAxis.cpy().crs(xAxis).nor();

        Matrix4 m = new Matrix4();
        /*m.val[M00] = xAxis.x;
        m.val[M01] = xAxis.y;
        m.val[M02] = xAxis.z;
        m.val[M03] = -(xAxis.cpy().dot(eye));

        m.val[M10] = yAxis.x;
        m.val[M11] = yAxis.y;
        m.val[M12] = yAxis.z;
        m.val[M13] = -(yAxis.cpy().dot(eye));

        m.val[M20] = zAxis.x;
        m.val[M21] = zAxis.y;
        m.val[M22] = zAxis.z;
        m.val[M23] = -(zAxis.cpy().dot(eye));

        m.tra();*/

        m.val[M00] = xAxis.x;
        m.val[M10] = xAxis.y;
        m.val[M20] = xAxis.z;
        m.val[M30] = -(xAxis.cpy().dot(eye));

        m.val[M01] = yAxis.x;
        m.val[M11] = yAxis.y;
        m.val[M21] = yAxis.z;
        m.val[M31] = -(yAxis.cpy().dot(eye));

        m.val[M02] = zAxis.x;
        m.val[M12] = zAxis.y;
        m.val[M22] = zAxis.z;
        m.val[M32] = -(zAxis.cpy().dot(eye));

        Collection<Entity> entities = ComponentUtils.getEntitiesMatchingComponent(entityManager.getEntities(), VertexComponent.class);
        for (Entity entity : entities) {
            Optional<Component> componentOptional = ComponentUtils.findFirstComponent(entity, VertexComponent.class);
            if (componentOptional.isPresent()) {
                VertexComponent vertexComponent = (VertexComponent) componentOptional.get();
                vertexComponent.getD().mul(m);
                java.lang.System.out.println(vertexComponent.getD().toString());
                break;
            }
        }
    }
}
