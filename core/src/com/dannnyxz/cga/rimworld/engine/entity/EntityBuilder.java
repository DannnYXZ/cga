package com.dannnyxz.cga.rimworld.engine.entity;

import com.dannnyxz.cga.rimworld.engine.component.Component;
import lombok.Setter;

public class EntityBuilder {
    @Setter
    private Entity entity;

    private final EntityManager entityManager;

    public EntityBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityBuilder builder() {
        EntityBuilder builder = new EntityBuilder(entityManager);
        builder.setEntity(entityManager.createEntity());
        return builder;
    }

    public EntityBuilder shortLivedBuilder() {
        EntityBuilder builder = new EntityBuilder(entityManager);
        builder.setEntity(entityManager.createShortLivedEntity());
        return builder;
    }

    public EntityBuilder with(Component component) {
        entity.with(component);
        return this;
    }

    public EntityBuilder addTo(int x, int y) {
        entityManager.addEntityTo(entity, x, y);
        return this;
    }

    public Entity build() {
        return entity;
    }

}
