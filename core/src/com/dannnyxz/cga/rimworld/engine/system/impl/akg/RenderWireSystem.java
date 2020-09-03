package com.dannnyxz.cga.rimworld.engine.system.impl.akg;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.akg.FaceComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.akg.VertexComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;

import java.util.Collection;
import java.util.Optional;

public class RenderWireSystem extends System {
    @Override
    public void processEntities(long tick) {
        Pixmap pixmap = new Pixmap(1920, 1080, Pixmap.Format.RGBA8888);
        Collection<Entity> entities = ComponentUtils.getEntitiesMatchingComponent(entityManager.getEntities(), FaceComponent.class);
        for (Entity entity : entities) {
            Optional<Component> componentOptional = ComponentUtils.findFirstComponent(entity, FaceComponent.class);
            if (componentOptional.isPresent()) {
                FaceComponent faceComponent = (FaceComponent) componentOptional.get();
                Optional<Component> x = ComponentUtils.findFirstComponent(faceComponent.getX(), VertexComponent.class);
                Optional<Component> y = ComponentUtils.findFirstComponent(faceComponent.getY(), VertexComponent.class);
                Optional<Component> z = ComponentUtils.findFirstComponent(faceComponent.getZ(), VertexComponent.class);
                if (x.isPresent() && y.isPresent() && z.isPresent()) {
                    VertexComponent x1 = (VertexComponent) x.get();
                    VertexComponent y1 = (VertexComponent) y.get();
                    VertexComponent z1 = (VertexComponent) z.get();

                    /*java.lang.System.out.println(x1.getD().toString());
                    java.lang.System.out.println(y1.getD().toString());
                    java.lang.System.out.println(z1.getD().toString());*/
                    //java.lang.System.exit(-1);

                    drawLine(x1.getD(), y1.getD(), pixmap);
                    drawLine(x1.getD(), z1.getD(), pixmap);
                    drawLine(z1.getD(), y1.getD(), pixmap);

                }
            }
        }
        SpriteBatch spriteBatch = new SpriteBatch();
        spriteBatch.begin();
        spriteBatch.draw(new Texture(pixmap), 0, 0);
        spriteBatch.end();
    }

    private void drawLine(Vector3 d1, Vector3 d2, Pixmap pixmap) {
        float x1 = d1.x;
        float y1 = d1.y;
        float x2 = d2.x;
        float y2 = d2.y;

        int L = (int) Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
        float x = x1;
        float y = y1;
        for (int i = 0; i < L; i++) {
            pixmap.drawPixel((int) x, (int) y, new Color(255, 0, 0, 1).toIntBits());
            x += (x2 - x1) / L;
            y += (y2 - y1) / L;
        }
    }
}
