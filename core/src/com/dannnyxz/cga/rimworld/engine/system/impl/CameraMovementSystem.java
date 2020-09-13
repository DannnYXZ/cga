package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.InputComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;
import com.dannnyxz.cga.rimworld.game.util.RenderUtils;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import static com.badlogic.gdx.Input.Keys;

@AllArgsConstructor
public class CameraMovementSystem extends System {
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer renderer;

    @Override
    public void processEntities(long tick) {
        Collection<Entity> shortLivedEntities = entityManager.getShortLivedEntities();

        Iterator<Entity> iterator = shortLivedEntities.iterator();
        //for (Entity entity : shortLivedEntities) {
        while (iterator.hasNext()) {
            Entity entity = iterator.next();

            Optional<Component> component = ComponentUtils.findFirstComponent(entity, InputComponent.class);

            if (component.isPresent()) {
                InputComponent inputComponent = (InputComponent) component.get();
                switch (inputComponent.getKeyCode()) {
                    case Keys.A: {
                        camera.zoom += 0.02;
                        break;
                    }
                    case Keys.Q: {
                        camera.zoom -= 0.02;
                        break;
                    }
                    case Keys.LEFT: {
                        camera.translate(-0.05f, 0, 0);
                        break;
                    }
                    case Keys.RIGHT: {
                        camera.translate(0.05f, 0, 0);
                        break;
                    }
                    case Keys.DOWN: {
                        camera.translate(0, -0.05f, 0);
                        break;
                    }
                    case Keys.UP: {
                        camera.translate(0, 0.05f, 0);
                        break;
                    }
                }
                //shortLivedEntities.remove(entity);
                iterator.remove();
            }
        }
        RenderUtils.updateCamera(camera);
        renderer.setView(camera);
    }
}
