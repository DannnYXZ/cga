package com.dannnyxz.cga;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.dannnyxz.cga.loader.ModelLoader;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.model.KeyboardEvent;
import com.dannnyxz.cga.model.KeyboardProcessor;
import com.dannnyxz.cga.model.Model;

public class Application extends ApplicationAdapter {

  Renderer renderer;
  Model model;
  Vec3 resolution;
  Camera camera;
  CameraDriver cameraDriver;
  KeyboardProcessor keyboardProcessor;

  @Override
  public void create() {
    renderer = new Renderer();
    resolution = new Vec3(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0);
    model = ModelLoader.load("models/Head/Model.obj");
    camera = new Camera(new Vec3(0, 0, 1), new Vec3(0, 1, 0), 0);
    cameraDriver = new CameraDriver(camera);
    keyboardProcessor = new KeyboardProcessor();
    keyboardProcessor.subscribe(new KeyboardEvent(), cameraDriver);
  }

  @Override
  public void render() {
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    Mat4 mProj = Mat4
        .projection(.01f, 100f, resolution.y / resolution.x, 1.1f);
//    Mat4 mView = Mat4.lookAt(new Vec3(0, .1f, 2), new Vec3(0, 0, 0));
    Mat4 mView = camera.getView();
    Mat4 mModel = new Mat4();
    Mat4 mScreen = Mat4.viewport(resolution.x, resolution.y);
    Mat4 transform = new Mat4();
    transform.mul(mScreen);
    transform.mul(mProj);
    transform.mul(mView);
    transform.mul(mModel);
    renderer.renderTriangle(model, transform);
    keyboardProcessor.processInput();
  }

  @Override
  public void dispose() {
  }
}
