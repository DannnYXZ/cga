package com.dannnyxz.cga.rimworld.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dannnyxz.cga.rimworld.game.RimworldGame;
import com.dannnyxz.cga.rimworld.game.util.RenderUtils;
import lombok.Getter;

public class StagedScreen implements Screen {
    @Getter
    protected Stage stage;

    @Getter
    protected InputMultiplexer multiplexer;

    @Getter
    protected Sprite background;

    @Getter
    protected RimworldGame game;

    @Getter
    protected OrthographicCamera camera;

    public StagedScreen() {
        stage = new Stage();

        multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(10, 10 * (height / width));
        RenderUtils.updateCamera(camera);

//        background = new Sprite(new Texture(Gdx.files.internal("textures/ui/widgets/desbutbg.png")));
//        background.setPosition(0, 0);
//        background.setSize(width, height);
    }

    public StagedScreen(RimworldGame game) {
        this();
        this.game = game;
    }

    public void setInput() {
        Gdx.input.setInputProcessor(multiplexer);
    }

    public void initialize() {
        Gdx.graphics.setContinuousRendering(true);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 0);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Batch batch = stage.getBatch();
        batch.begin();
        background.draw(batch);
        batch.end();
    }

    protected void act() {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }
}
