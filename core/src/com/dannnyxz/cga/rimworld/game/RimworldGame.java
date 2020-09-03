package com.dannnyxz.cga.rimworld.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.dannnyxz.cga.rimworld.game.screens.GameScreen;
import com.dannnyxz.cga.rimworld.game.screens.StagedScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RimworldGame extends Game {
    public List<StagedScreen> screens;
    public static TextureAtlas atlas;
    public static TextureAtlas terrainAtlas;
    public static I18NBundle bundle;
    public static Locale locale;
    public static BitmapFont standard;

    public TextButton.TextButtonStyle textButtonStyle;
    public Label.LabelStyle labelStyle;
    public TextField.TextFieldStyle textFieldStyle;
    public Slider.SliderStyle sliderStyle;
    public Window.WindowStyle windowStyle;

    @Override
    public void create() {
        locale = new Locale("ru");
//        atlas = new TextureAtlas(Gdx.files.internal("textures/world/biomes/biomesPacked.atlas"));
//        terrainAtlas = new TextureAtlas(Gdx.files.internal("textures/terrains/terrainsPacked.atlas"));
//        standard = new BitmapFont(Gdx.files.internal("fonts/arial_24.fnt"));
        //reloadBundle();

        createStyles();

        screens = new ArrayList<>();

        screens.add(new GameScreen(this));

        showScreen(0);
    }

    private void createStyles() {
//        textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = standard;
//        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/buttonbg.png"))));
//        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/buttonbgclick.png"))));
//        textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/buttonbgmouseover.png"))));

        labelStyle = new Label.LabelStyle();
        labelStyle.font = standard;

//        textFieldStyle = new TextField.TextFieldStyle();
//        textFieldStyle.font = standard;
//        textFieldStyle.fontColor = new Color(1, 1, 1, 1);
//        textFieldStyle.background = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/buttonsubtleatlas.png"))
//        ));
//
//        sliderStyle = new Slider.SliderStyle();
//        sliderStyle.knob = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/slider_knob.png"))
//        ));
//        sliderStyle.knobDown = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/slider_knob_active.png"))
//        ));
//        sliderStyle.knobOver = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/slider_knob_hover.png"))
//        ));
//        sliderStyle.background = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/buttonsubtleatlas.png"))
//        ));

        windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = standard;
        windowStyle.titleFontColor = new Color(1, 1, 1, 1);
//        windowStyle.background = new TextureRegionDrawable(new TextureRegion(
//                new Texture(Gdx.files.internal("textures/ui/widgets/desbutbg.png"))
//        ));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        for (StagedScreen screen : screens) {
            screen.dispose();
        }
    }

    public void showScreen(int screenNum) {
        StagedScreen screen = screens.get(screenNum);
        screen.setInput();
        screen.initialize();
        setScreen(screen);
    }

    private static void reloadBundle() {
        bundle = I18NBundle.createBundle(Gdx.files.internal("i18n/bundle"), locale);
    }
}
