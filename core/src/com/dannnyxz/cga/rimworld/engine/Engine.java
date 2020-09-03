package com.dannnyxz.cga.rimworld.engine;

import com.dannnyxz.cga.rimworld.engine.entity.EntityBuilder;
import com.dannnyxz.cga.rimworld.engine.entity.EntityManager;
import com.dannnyxz.cga.rimworld.engine.system.System;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class Engine {

    private final List<System> systems = new ArrayList<>();

    private long tick = 0L;

    @Getter
    private final EntityManager entityManager;

    @Getter
    private final EntityBuilder entityBuilder;

    public Engine() {
        entityManager = new EntityManager();
        entityBuilder = new EntityBuilder(entityManager);
    }

    public void addSystem(System system) {
        systems.add(system);
        system.setEntityBuilder(entityBuilder);
        system.setEntityManager(entityManager);
    }

    public void runTick() {
        for (System system : systems) {
            system.processEntities(tick);
        }
        tick++;
    }

}
