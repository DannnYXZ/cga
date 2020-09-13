package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.TextureComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

@AllArgsConstructor
public class ObjectRenderSystem extends System {
    private final OrthogonalTiledMapRenderer renderer;

    @Override
    public void processEntities(long tick) {
        List<Pair<Long, TextureComponent>> zTextureComponents = new ArrayList<>();
        long zIndex = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 100 - 1; j >= 0; j--) {
                Collection<Entity> entities = entityManager.getEntitiesFrom(i, j);
                for (Entity object : entities) {
                    Optional<Component> optionalTexture = ComponentUtils.findFirstComponent(object, TextureComponent.class);

                    if (optionalTexture.isPresent()) {
                        TextureComponent textureComponent = (TextureComponent) optionalTexture.get();
                        zTextureComponents.add(new ImmutablePair<>(zIndex, textureComponent));
                        zIndex++;
                    }
                }
            }
        }
        zTextureComponents.sort(Comparator.comparing(Pair::getLeft));
        Map<Texture, List<TextureComponent>> textureListMap = new HashMap<>();
        for (Pair<Long, TextureComponent> pair : zTextureComponents) {
            TextureComponent textureComponent = pair.getRight();
            Texture texture = textureComponent.getSprite().getTexture();
            if (!textureListMap.containsKey(texture)) {
                List<TextureComponent> textureComponents = new ArrayList<>();
                textureComponents.add(textureComponent);
                textureListMap.put(texture, textureComponents);
            } else {
                textureListMap.get(texture).add(textureComponent);
            }
        }
        Batch batch = renderer.getBatch();
        batch.begin();
        for (List<TextureComponent> textureComponents : textureListMap.values()) {
            for (TextureComponent textureComponent : textureComponents) {
                batch.draw(textureComponent.getSprite(),
                        textureComponent.getX(),
                        textureComponent.getY(),
                        textureComponent.getWidth(),
                        textureComponent.getHeight());
            }
        }
        batch.end();
    }
}
