package com.dannnyxz.cga;

import static java.lang.Math.max;
import static java.lang.Math.min;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dannnyxz.cga.loader.ModelLoader;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import com.dannnyxz.cga.model.KeyboardEvent;
import com.dannnyxz.cga.model.KeyboardProcessor;
import com.dannnyxz.cga.model.Model;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class Application extends ApplicationAdapter {

  Model model;
  Camera camera;
  CameraDriver cameraDriver;
  KeyboardProcessor keyboardProcessor;
  Pixmap pixmap;
  SpriteBatch spriteBatch;
  Texture tex;
  MutablePair<Integer, Integer> windowResolution;
  ShaderProgram program = new ShaderProgram();

  @Override
  public void create() {
    windowResolution = new MutablePair<>(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    model = ModelLoader.load("models/Head/Model.obj");
    camera = new Camera(new Vec3(0, 0, 1), new Vec3(0, 1, 0), 0);
    cameraDriver = new CameraDriver(camera);
    keyboardProcessor = new KeyboardProcessor();
    keyboardProcessor.subscribe(new KeyboardEvent(), cameraDriver);
    spriteBatch = new SpriteBatch();
    pixmap = createScreenPixmap();
    tex = new Texture(pixmap);
    program.enableZBuffer(pixmap.getWidth(), pixmap.getHeight());
  }

  private Pixmap createScreenPixmap() {
    Pixmap pixmap = new Pixmap(
        Gdx.graphics.getWidth(),
        Gdx.graphics.getHeight(),
        Pixmap.Format.RGBA8888
    );
    pixmap.setColor(new Color(1, 1, 1, 1));
    return pixmap;
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    pixmap.fill();
    Vec3 resolution = new Vec3(windowResolution.left, windowResolution.right, 0);

    Mat4 mProj = Mat4
        .projection(-0.5f, -10f,
            max(resolution.x, resolution.y) / min(resolution.x, resolution.y),
            1.1f);
//    Mat4 mView = Mat4.lookAt(new Vec3(0, .1f, 2), new Vec3(0, 0, 0));
    Mat4 mView = camera.getView();
    Mat4 mModel = new Mat4();
    Mat4 mScreen = Mat4.viewport(resolution.x, resolution.y);
    Mat4 transform = new Mat4();
    transform.mul(mScreen);
    transform.mul(mProj);
    transform.mul(mView);
    transform.mul(mModel);

    program.uniforms().put("model", mModel);
    program.uniforms().put("view", mView);
    program.uniforms().put("proj", mProj);
    program.uniforms().put("transform", transform);
    program.uniforms().put("lightSource", new Vec3(1, 1, 1).mul(mView));
    program.drawFaces(model.polygons, pixmap);
//    renderer.renderModel(model, transform, pixmap);
    tex.draw(pixmap, 0, 0);
    spriteBatch.begin();
    spriteBatch.draw(tex, 0, 0);
    spriteBatch.end();
    keyboardProcessor.processInput();
//    detectResolutionChange(windowResolution);
    System.out.println(windowResolution.toString());
  }

//  private void detectResolutionChange(Pair<Integer, Integer> prevResolution) {
//    if (Gdx.graphics.getWidth() != prevResolution.getLeft()
//        || Gdx.graphics.getHeight() != prevResolution.getRight()) {
//      windowResolution.setLeft(windowResolution.getLeft());
//      windowResolution.setRight(windowResolution.getRight());
//      pixmap.dispose();
//      pixmap = createScreenPixmap();
//    }
//  }

  @Override
  public void dispose() {
    pixmap.dispose();
    spriteBatch.dispose();
  }
}
