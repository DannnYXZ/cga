package com.dannnyxz.cga.rimworld.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.dannnyxz.cga.rimworld.engine.Engine;
import com.dannnyxz.cga.rimworld.engine.component.impl.*;
import com.dannnyxz.cga.rimworld.engine.component.impl.akg.FaceComponent;
import com.dannnyxz.cga.rimworld.engine.component.impl.akg.VertexComponent;
import com.dannnyxz.cga.rimworld.engine.entity.Entity;
import com.dannnyxz.cga.rimworld.engine.system.impl.*;
import com.dannnyxz.cga.rimworld.engine.system.impl.akg.InitVertexSystem;
import com.dannnyxz.cga.rimworld.engine.system.impl.akg.OrthographicProjectionSystem;
import com.dannnyxz.cga.rimworld.engine.system.impl.akg.ProjectionSpaceSystem;
import com.dannnyxz.cga.rimworld.engine.system.impl.akg.RenderWireSystem;
import com.dannnyxz.cga.rimworld.engine.system.impl.akg.ViewSpaceSystem;
import com.dannnyxz.cga.rimworld.engine.system.impl.akg.ViewportSpaceSystem;
import com.dannnyxz.cga.rimworld.game.RimworldGame;
import com.dannnyxz.cga.rimworld.game.util.RenderUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameScreen extends StagedScreen {

    private static final List<Integer> processedKeys = Arrays.asList(
            Input.Keys.Q,
            Input.Keys.A,
            Input.Keys.LEFT,
            Input.Keys.RIGHT,
            Input.Keys.UP,
            Input.Keys.DOWN
    );

    private Engine engine;

    private FPSLogger logger = new FPSLogger();

    public GameScreen(RimworldGame game) {
        super(game);
    }

    /*@Override
    public void initialize() {
        engine = new Engine();

        Gdx.graphics.setContinuousRendering(true);

        RenderUtils.updateCamera(camera);

        multiplexer.addProcessor(new EntityInputAdapter(engine));

        Random random = new Random();

        Texture texture = new Texture(Gdx.files.internal("textures/treeoaka.png"));
        TextureRegion region = new TextureRegion(new Texture(Gdx.files.internal("textures/linked/wall/wall_atlas_bricks.png")));
        TextureRegion[][] regions = region.split(80, 80);
        for (TextureRegion[] regions1 : regions) {
            for (TextureRegion region1 : regions1) {
                region1.setRegionX(region1.getRegionX() + 8);
                region1.setRegionY(region1.getRegionY() + 8);
                region1.setRegionHeight(64);
                region1.setRegionWidth(64);
            }
        }
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                engine.getEntityBuilder().builder()
                        .with(new IntPositionComponent(i, j))
                        .with(new MapTextureComponent(RimworldGame.terrainAtlas.findRegion("Soil"), "Soil"))
                        .addTo(i, j);
                if (random.nextBoolean()) {
                    engine.getEntityBuilder().builder()
                            .with(new IntPositionComponent(i, j))
                            .with(new TextureComponent(new Sprite(texture), i, j, 1, 1))
                            .addTo(i, j);
                } else {
                    Entity entity = engine.getEntityBuilder().builder()
                            .with(new IntPositionComponent(i, j))
                            .with(new TextureComponent(new Sprite(regions[0][0]), i, j, 1, 1))
                            .with(new LinkedObjectComponent(regions, "wall", -1))
                            .addTo(i, j)
                            .build();
                    engine.getEntityBuilder().shortLivedBuilder()
                            .with(new ObjectCreatedComponent(entity.getId()));
                }
            }
        }
        Texture pawn = new Texture(Gdx.files.internal("textures/iconhuman.png"));
        engine.getEntityBuilder().builder()
                .with(new IntPositionComponent(0, 0))
                .with(new SpeedComponent(0.05f))
                .with(new TextureComponent(new Sprite(new TextureRegion(pawn)), 0, 0, 1, 1))
                .with(new MovementComponent(0, 0, 0))
                .with(new PlayerControlComponent())
                .addTo(0, 0);

        OrthogonalTiledMapRenderer renderer = new OrthogonalTiledMapRenderer(new TiledMap(), 1 / 64f, new SpriteBatch(5000));
        engine.addSystem(new ControlSystem(camera));
        engine.addSystem(new CameraMovementSystem(camera, renderer));
        engine.addSystem(new MapRenderSystem(renderer));
        engine.addSystem(new PathfinderSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new LinkedObjectSystem());
        engine.addSystem(new ObjectRenderSystem(renderer));
        //engine.addSystem(new FpsRenderSystem(renderer));
        engine.addSystem(new ClearShortLivedSystem());
    }*/

    @Override
    public void initialize() {
        engine = new Engine();

        Gdx.graphics.setContinuousRendering(true);

        multiplexer.addProcessor(new EntityInputAdapter(engine));

        ArrayList<Entity> vertices = new ArrayList<>();
        FileHandle file = Gdx.files.internal("models/Head/Model.obj");
        try (BufferedReader br = new BufferedReader(file.reader())) {
            br.lines().forEach(s -> {
                if (s.startsWith("v ")) {
                    s = s.replaceAll("\\s+", " ");
                    String[] strings = s.split(" ");
                    try {
                        float x = Float.parseFloat(strings[1]);
                        float y = Float.parseFloat(strings[2]);
                        float z = Float.parseFloat(strings[3]);
                        /*x += 1;
                        y += 1;
                        z += 1;
                        x *= 960;
                        y *= 540;*/
                        Vector3 v = new Vector3(x, y, z);
                        Entity e = engine.getEntityManager().createEntity()
                                .with(new VertexComponent(v, v.cpy()));
                        vertices.add(e);
                    } catch (NumberFormatException e) {

                    }
                } else if (s.startsWith("f ")) {
                    s = s.replaceAll(" +", " ");
                    String[] strings = s.split(" ");
                    try {
                        int x = Integer.parseInt(strings[1].split("/")[0]);
                        int y = Integer.parseInt(strings[2].split("/")[0]);
                        int z = Integer.parseInt(strings[3].split("/")[0]);
                        x--;
                        y--;
                        z--;
                        engine.getEntityManager().createEntity()
                                .with(new FaceComponent(vertices.get(x), vertices.get(y), vertices.get(z)));
                    } catch (NumberFormatException e) {

                    }
                }
            });
        } catch (IOException e) {

        }

        engine.addSystem(new InitVertexSystem());
        engine.addSystem(new OrthographicProjectionSystem());
        engine.addSystem(new RenderWireSystem());
        engine.addSystem(new ClearShortLivedSystem());
    }

    private void handlePressedKeys() {
        for (Integer keyCode : processedKeys) {
            if (Gdx.input.isKeyPressed(keyCode)) {
                engine.getEntityBuilder().shortLivedBuilder()
                        .with(new InputComponent(keyCode));
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        logger.log();
        handlePressedKeys();
        engine.runTick();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private static class EntityInputAdapter extends InputAdapter {

        private final Engine engine;

        public EntityInputAdapter(Engine engine) {
            super();
            this.engine = engine;
        }

        @Override
        public boolean keyDown(int keyCode) {
            // Here are keys that must be handled as one press, not continued
            /*engine.getEntityBuilder().shortLivedBuilder()
                    .with(new InputComponent(keyCode));*/
            return true;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            engine.getEntityBuilder().shortLivedBuilder()
                    .with(new MouseInputComponent(button, screenX, screenY));
            return true;
        }
    }
}
