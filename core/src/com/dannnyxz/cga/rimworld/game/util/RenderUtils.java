package com.dannnyxz.cga.rimworld.game.util;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;

public class RenderUtils {

    public static void updateCamera(OrthographicCamera camera) {
        // for now size is hardcoded, it should be a parameter of world
        float size = 64;
        camera.zoom = MathUtils.clamp(camera.zoom, 0.5f, size / camera.viewportWidth);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, size - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, size - effectiveViewportHeight / 2f);

        camera.update();
    }

}
