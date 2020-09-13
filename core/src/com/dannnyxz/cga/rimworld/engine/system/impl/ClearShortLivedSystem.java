package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.dannnyxz.cga.rimworld.engine.system.System;

public class ClearShortLivedSystem extends System {
    @Override
    public void processEntities(long tick) {
        entityManager.clearShortLivedEntities();
    }
}
