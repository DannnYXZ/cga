package com.dannnyxz.cga.rimworld.game.util;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ComponentUtils {

    public static boolean hasComponent(Entity entity, Class<? extends Component> clazz) {
        return entity.getComponents().containsKey(clazz.getSimpleName());
    }

    public static boolean hasComponentsArray(Entity entity, List<Class<? extends Component>> classList) {
        return classList
                .stream()
                .allMatch(aClass -> hasComponent(entity, aClass));
    }

    @SafeVarargs
    public static boolean hasComponents(Entity entity, Class<? extends Component>... classes) {
        return Arrays.stream(classes)
                .allMatch(aClass -> hasComponent(entity, aClass));
    }

    public static List<Entity> getEntitiesMatchingComponent(Collection<Entity> entities, Class<? extends Component> clazz) {
        return entities
                .stream()
                .filter(entity -> hasComponent(entity, clazz))
                .collect(Collectors.toList());
    }

    @SafeVarargs
    public static List<Entity> getEntitiesMatchingComponents(Collection<Entity> entities, Class<? extends Component>... classes) {
        return entities
                .stream()
                .filter(entity -> hasComponents(entity, classes))
                .collect(Collectors.toList());
    }

    public static Optional<Component> findFirstComponent(Entity entity, Class<? extends Component> clazz) {
        return Optional.ofNullable(entity.getComponents().getOrDefault(clazz.getSimpleName(), null));
    }

    public static Component getFirstComponent(Entity entity, Class<? extends Component> clazz) {
        return entity.getComponents().getOrDefault(clazz.getSimpleName(), null);
    }

}
