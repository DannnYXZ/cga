package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dannnyxz.cga.rimworld.engine.system.System;

public class FpsRenderSystem extends System {

    private final OrthogonalTiledMapRenderer renderer;

    private final BitmapFont bitmapFont;

    public FpsRenderSystem(OrthogonalTiledMapRenderer renderer) {
        this.renderer = renderer;
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(255, 0, 0, 1);
    }

    @Override
    public void processEntities(long tick) {
        renderer.getBatch().begin();
        bitmapFont.draw(renderer.getBatch(), String.valueOf(Gdx.graphics.getFramesPerSecond()), 5, 5);
        renderer.getBatch().end();
    }
}
