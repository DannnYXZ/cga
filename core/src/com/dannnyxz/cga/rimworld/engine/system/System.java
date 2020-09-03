package com.dannnyxz.cga.rimworld.engine.system;

import com.dannnyxz.cga.rimworld.engine.entity.EntityBuilder;
import com.dannnyxz.cga.rimworld.engine.entity.EntityManager;
import lombok.Setter;

public abstract class System {
    @Setter
    protected EntityManager entityManager;
    @Setter
    protected EntityBuilder entityBuilder;

    public abstract void processEntities(long tick);
}
