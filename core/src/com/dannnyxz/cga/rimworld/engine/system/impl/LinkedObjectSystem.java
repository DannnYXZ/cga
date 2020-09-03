package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.IntPositionComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.LinkedObjectComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.ObjectCreatedComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.TextureComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;

import java.util.Collection;
import java.util.Optional;

public class LinkedObjectSystem extends System {
    @Override
    public void processEntities(long tick) {
        Collection<Entity> shortLivedEntities = entityManager.getShortLivedEntities();
        for (Entity object : shortLivedEntities) {
            Optional<Component> optionalCreated = ComponentUtils.findFirstComponent(object, ObjectCreatedComponent.class);
            if (optionalCreated.isPresent()) {
                ObjectCreatedComponent objectCreatedComponent = (ObjectCreatedComponent) optionalCreated.get();
                Entity entity = entityManager.getEntity(objectCreatedComponent.getId());

                if (ComponentUtils.hasComponent(entity, LinkedObjectComponent.class)) {
                    Optional<Component> optionalPosition = ComponentUtils.findFirstComponent(entity, IntPositionComponent.class);
                    if (optionalPosition.isPresent()) {
                        IntPositionComponent intPositionComponent = (IntPositionComponent) optionalPosition.get();
                        int x = intPositionComponent.getX();
                        int y = intPositionComponent.getY();

                        checkNearbyObjects(x, y);
                        checkNearbyObjects(x + 1, y);
                        checkNearbyObjects(x - 1, y);
                        checkNearbyObjects(x, y + 1);
                        checkNearbyObjects(x, y - 1);
                    }
                }
            }
        }
    }

    private void checkNearbyObjects(int i, int j) {
        if (i >= 0 && j >= 0 && i < 100 && j < 100) {
            Collection<Entity> entities = entityManager.getEntitiesFrom(i, j);
            for (Entity object : entities) {
                Optional<Component> optionalLinked = ComponentUtils.findFirstComponent(object, LinkedObjectComponent.class);
                if (optionalLinked.isPresent()) {
                    LinkedObjectComponent linkedObjectComponent = (LinkedObjectComponent) optionalLinked.get();

                    int index = 0;
                    if (checkNearbyObject(linkedObjectComponent, i, j + 1)) {
                        index |= 1;
                    }
                    if (checkNearbyObject(linkedObjectComponent, i + 1, j)) {
                        index |= 2;
                    }
                    if (checkNearbyObject(linkedObjectComponent, i, j - 1)) {
                        index |= 4;
                    }
                    if (checkNearbyObject(linkedObjectComponent, i - 1, j)) {
                        index |= 8;
                    }
                    if (linkedObjectComponent.getIndex() != index) {
                        linkedObjectComponent.setIndex(index);
                        Optional<Component> optionalTexture = ComponentUtils.findFirstComponent(object, TextureComponent.class);
                        if (optionalTexture.isPresent()) {
                            TextureComponent textureComponent = (TextureComponent) optionalTexture.get();
                            int x = 3 - (index / 4);
                            int y = (index % 4);
                            textureComponent.getSprite().setRegion(linkedObjectComponent.getRegions()[x][y]);
                        }
                    }
                }
            }
        }
    }

    private boolean checkNearbyObject(LinkedObjectComponent linkedObjectComponent, int i, int j) {
        if (i >= 0 && j >= 0 && i < 100 && j < 100) {
            Collection<Entity> otherEntities = entityManager.getEntitiesFrom(i, j);
            for (Entity otherObject : otherEntities) {
                Optional<Component> otherOptionalLinked = ComponentUtils.findFirstComponent(otherObject, LinkedObjectComponent.class);
                if (otherOptionalLinked.isPresent()) {
                    LinkedObjectComponent linkedObjectComponent1 = (LinkedObjectComponent) otherOptionalLinked.get();
                    if (linkedObjectComponent.getType().equals(linkedObjectComponent1.getType())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
