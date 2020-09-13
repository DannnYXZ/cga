package com.dannnyxz.cga.rimworld.engine.system.impl;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.dannnyxz.cga.rimworld.engine.component.Component;
import com.dannnyxz.cga.rimworld.engine.component.impl.IntPositionComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.MapTextureComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.System;
import com.dannnyxz.cga.rimworld.game.util.ComponentUtils;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class MapRenderSystem extends System {
    private final OrthogonalTiledMapRenderer renderer;
    private final TiledMapTileSet tileSet = new TiledMapTileSet();
    private final Map<String, Integer> tileIdMap = new HashMap<>();

    @Override
    public void processEntities(long tick) {
        Batch batch = renderer.getBatch();
        Collection<Entity> entities = entityManager.getEntities();

        TiledMapTileLayer layer = new TiledMapTileLayer(100, 100, 64, 64);
        for (Entity mapTile : entities) {
            Optional<Component> optionalPosition = ComponentUtils.findFirstComponent(mapTile, IntPositionComponent.class);
            Optional<Component> optionalMapTexture = ComponentUtils.findFirstComponent(mapTile, MapTextureComponent.class);

            if (optionalPosition.isPresent() && optionalMapTexture.isPresent()) {
                IntPositionComponent positionComponent = (IntPositionComponent) optionalPosition.get();
                MapTextureComponent mapTextureComponent = (MapTextureComponent) optionalMapTexture.get();

                TiledMapTile tile = getOrCreateTileInTileSet(tileSet, tileIdMap, mapTextureComponent);
                layer.setCell(positionComponent.getX(), positionComponent.getY(), new TiledMapTileLayer.Cell().setTile(tile));
            }
        }
        batch.begin();
        batch.disableBlending();
        renderer.renderTileLayer(layer);
        batch.enableBlending();
        batch.end();
    }

    private TiledMapTile getOrCreateTileInTileSet(TiledMapTileSet tiledMapTileSet,
                                                  Map<String, Integer> tileIdMap,
                                                  MapTextureComponent component) {
        int id;
        if (tileIdMap.containsKey(component.getId())) {
            id = tileIdMap.get(component.getId());
        } else {
            id = tileIdMap.size();
            tileIdMap.put(component.getId(), id);
            TiledMapTile tile = new StaticTiledMapTile(component.getTexture());
            tiledMapTileSet.putTile(id, tile);
            return tile;
        }
        return tiledMapTileSet.getTile(id);
    }
}
