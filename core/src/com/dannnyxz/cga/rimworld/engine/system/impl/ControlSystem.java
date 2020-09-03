package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.MouseInputComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.PlayerControlComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.TargetComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ControlSystem extends System {
    private final OrthographicCamera camera;

    @Override
    public void processEntities(long tick) {
        Collection<Entity> entities = entityManager.getEntities();
        Collection<Entity> shortLivedEntities = entityManager.getShortLivedEntities();

        Iterator<Entity> iterator = shortLivedEntities.iterator();
        List<Entity> controlledEntities = ComponentUtils.getEntitiesMatchingComponent(entities, PlayerControlComponent.class);

        //for (Entity entity : shortLivedEntities) {
        while (iterator.hasNext()) {
            Entity entity = iterator.next();

            Optional<Component> component = ComponentUtils.findFirstComponent(entity, MouseInputComponent.class);

            if (component.isPresent()) {
                MouseInputComponent inputComponent = (MouseInputComponent) component.get();
                if (inputComponent.getButton() == Input.Buttons.RIGHT) {
                    Vector3 vec = camera.unproject(new Vector3(inputComponent.getX(), inputComponent.getY(), 0));
                    for (Entity controlled : controlledEntities) {
                        Optional<Component> optionalTarget = ComponentUtils.findFirstComponent(controlled, TargetComponent.class);
                        if (optionalTarget.isPresent()) {
                            TargetComponent target = (TargetComponent) optionalTarget.get();
                            target.setX((int) vec.x);
                            target.setY((int) vec.y);
                        } else {
                            TargetComponent target = new TargetComponent((int) vec.x, (int) vec.y);
                            controlled.with(target);
                        }
                    }
                }
                //shortLivedEntities.remove(entity);
                iterator.remove();
            }
        }
    }
}
