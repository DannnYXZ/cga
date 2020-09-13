package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.badlogic.gdx.math.Vector2;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.IntPositionComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.MovementComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.SpeedComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.TextureComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MovementSystem extends System {
    @Override
    public void processEntities(long tick) {
        Collection<Entity> entities = entityManager.getEntities();

        for (Entity entity : entities) {
            Optional<Component> optionalMovement = ComponentUtils.findFirstComponent(entity, MovementComponent.class);

            if (optionalMovement.isPresent()) {
                Optional<Component> optionalPosition = ComponentUtils.findFirstComponent(entity, IntPositionComponent.class);
                Optional<Component> optionalTexture = ComponentUtils.findFirstComponent(entity, TextureComponent.class);
                Optional<Component> optionalSpeed = ComponentUtils.findFirstComponent(entity, SpeedComponent.class);

                if (optionalTexture.isPresent() && optionalPosition.isPresent() && optionalSpeed.isPresent()) {

                    TextureComponent textureComponent = (TextureComponent) optionalTexture.get();
                    IntPositionComponent positionComponent = (IntPositionComponent) optionalPosition.get();
                    MovementComponent movementComponent = (MovementComponent) optionalMovement.get();
                    SpeedComponent speedComponent = (SpeedComponent) optionalSpeed.get();

                    float delta = movementComponent.getDelta() + speedComponent.getSpeed();
                    movementComponent.setDelta(delta);

                    Vector2 target = new Vector2(movementComponent.getDeltaX(), movementComponent.getDeltaY());
                    if (delta >= target.len()) {
                        entityManager.removeEntityFrom(entity, positionComponent.getX(), positionComponent.getY());
                        positionComponent.setX(positionComponent.getX() + movementComponent.getDeltaX());
                        positionComponent.setY(positionComponent.getY() + movementComponent.getDeltaY());
                        entityManager.addEntityTo(entity, positionComponent.getX(), positionComponent.getY());

                        textureComponent.setX(positionComponent.getX());
                        textureComponent.setY(positionComponent.getY());

                        movementComponent.setDelta(0);
                        movementComponent.setDeltaX(0);
                        movementComponent.setDeltaY(0);
                    } else {
                        Vector2 vec = new Vector2();
                        Vector2 result = vec.lerp(target, movementComponent.getDelta() / target.len());
                        textureComponent.setX(positionComponent.getX() + result.x);
                        textureComponent.setY(positionComponent.getY() + result.y);
                    }
                }
            }
        }
    }
}
