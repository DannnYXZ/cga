package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.IntPositionComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.MovementComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.PlayerControlComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.TargetComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PathfinderSystem extends System {
    @Override
    public void processEntities(long tick) {
        Collection<Entity> entities = entityManager.getEntities();

        List<Entity> controlledEntities = ComponentUtils.getEntitiesMatchingComponent(entities, PlayerControlComponent.class);
        for (Entity entity : controlledEntities) {
            Optional<Component> optionalIntPosition = ComponentUtils.findFirstComponent(entity, IntPositionComponent.class);

            if (optionalIntPosition.isPresent()) {
                IntPositionComponent intPositionComponent = (IntPositionComponent) optionalIntPosition.get();
                Optional<Component> optionalTarget = ComponentUtils.findFirstComponent(entity, TargetComponent.class);

                if (optionalTarget.isPresent()) {
                    TargetComponent targetComponent = (TargetComponent) optionalTarget.get();
                    int targetX = targetComponent.getX();
                    int targetY = targetComponent.getY();
                    int positionX = intPositionComponent.getX();
                    int positionY = intPositionComponent.getY();
                    if (targetX != positionX || targetY != positionY) {
                        Optional<Component> optionalMovement = ComponentUtils.findFirstComponent(entity, MovementComponent.class);
                        MovementComponent movementComponent;
                        if (optionalMovement.isPresent()) {
                            movementComponent = (MovementComponent) optionalMovement.get();
                        } else {
                            movementComponent = new MovementComponent(0, 0, 0);
                        }
                        if (movementComponent.getDeltaX() == 0 && movementComponent.getDeltaY() == 0) {
                            if (targetX != positionX) {
                                int deltaX = targetX - positionX;
                                movementComponent.setDeltaX(deltaX / Math.abs(deltaX));
                            }
                            if (targetY != intPositionComponent.getY()) {
                                int deltaY = targetY - positionY;
                                movementComponent.setDeltaY(deltaY / Math.abs(deltaY));
                            }
                            movementComponent.setDelta(0);
                        }
                    }
                }
            }
        }
    }
}
