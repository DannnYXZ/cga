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

public class OrthographicProjectionSystem extends System {
    @Override
    public void processEntities(long tick) {
        Vector3 eye = new Vector3(0, 0, 1);
        Vector3 target = new Vector3(0, 0, 0);
        Vector3 up = new Vector3(0, 1, 0);

        Vector3 zAxis = eye.cpy().sub(target).nor();
        Vector3 xAxis = up.cpy().crs(zAxis).nor();
        Vector3 yAxis = zAxis.cpy().crs(xAxis).nor();

        Matrix4 view = new Matrix4();
        view.val[M00] = xAxis.x;
        view.val[M01] = xAxis.y;
        view.val[M02] = xAxis.z;
        view.val[M03] = -(xAxis.cpy().dot(eye));

        view.val[M10] = yAxis.x;
        view.val[M11] = yAxis.y;
        view.val[M12] = yAxis.z;
        view.val[M13] = -(yAxis.cpy().dot(eye));

        view.val[M20] = zAxis.x;
        view.val[M21] = zAxis.y;
        view.val[M22] = zAxis.z;
        view.val[M23] = -(zAxis.cpy().dot(eye));

        float right = 960;
        float left = -960;
        float top = 540;
        float bottom = -540;
        float near = 0;
        float far = 1;

        Matrix4 projection = new Matrix4();
        float width = 1920;
        float height = 1080;
        float xmin = 0;
        float ymin = 0;

        Matrix4 viewport = new Matrix4();

        viewport.val[M00] = width / 2;
        viewport.val[M03] = xmin + width / 2;

        viewport.val[M11] = -height / 2;
        viewport.val[M13] = ymin + height / 2;

        //Matrix4 combined = viewport.cpy().mul(projection).mul(view);
        //Matrix4 combined = projection.cpy().mul(view);
        Matrix4 combined = viewport.cpy().mul(view);
        Matrix4 m = new Matrix4();
        m.mul(viewport).mul(projection).mul(view);

        Collection<Entity> entities = ComponentUtils
            .getEntitiesMatchingComponent(entityManager.getEntities(), VertexComponent.class);
        for (Entity entity : entities) {
            Optional<Component> componentOptional = ComponentUtils.findFirstComponent(entity, VertexComponent.class);
            if (componentOptional.isPresent()) {
                VertexComponent vertexComponent = (VertexComponent) componentOptional.get();
                //java.lang.System.out.println(vertexComponent.getD().toString());
                vertexComponent.getD().mul(m);
                //java.lang.System.out.println(vertexComponent.getD().toString());
            }
        }
    }
}
