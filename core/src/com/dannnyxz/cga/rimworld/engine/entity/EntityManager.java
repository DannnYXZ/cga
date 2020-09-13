package com.dannnyxz.cga.rimworld.engine.entity;

import java.util.*;

public class EntityManager {
    private long nextId = 0L;

    private final EntitiesMap entityMap = new EntitiesMap();
    private final List<Entity> shortLivedEntities = new ArrayList<>();

    private final EntitiesMap[][] entityMatrix = new EntitiesMap[100][100];

    public EntityManager() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                entityMatrix[i][j] = new EntitiesMap();
            }
        }
    }

    public Entity createEntity() {
        Entity entity = new Entity(nextId);
        entityMap.put(nextId, entity);
        nextId++;
        return entity;
    }

    public Entity createShortLivedEntity() {
        Entity entity = new Entity(-1);
        shortLivedEntities.add(entity);
        return entity;
    }

    public void clearShortLivedEntities() {
        shortLivedEntities.clear();
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public Entity getEntity(long id) {
        return entityMap.get(id);
    }

    public Collection<Entity> getShortLivedEntities() {
        return shortLivedEntities;
    }

    public void destroyEntity(long id) {
        entityMap.remove(id);
    }

    public void addEntityTo(Entity entity, int x, int y) {
        entityMatrix[x][y].put(entity.getId(), entity);
    }

    public void removeEntityFrom(Entity entity, int x, int y) {
        entityMatrix[x][y].remove(entity.getId());
    }

    public Collection<Entity> getEntitiesFrom(int x, int y) {
        return entityMatrix[x][y].values();
    }
}
