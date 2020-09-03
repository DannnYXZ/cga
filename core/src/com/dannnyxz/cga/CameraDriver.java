package com.dannnyxz.cga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.model.EventListener;

public class CameraDriver implements EventListener {

  private Camera camera;
  private float moveSpeed = 1.f;
  private float rotSpeed = 1.f;

  public CameraDriver setRotSpeed(float rotSpeed) {
    this.rotSpeed = rotSpeed;
    return this;
  }

  public void setMoveSpeed(float moveSpeed) {
    this.moveSpeed = moveSpeed;
  }

  public CameraDriver(Camera camera) {
    this.camera = camera;
  }

  @Override
  public void update(Object data) {
    Integer keyCode = (Integer) data;
    float dt = Gdx.graphics.getDeltaTime();
    if (keyCode == Keys.A) {
      camera.move(new Vec3(-1, 0, 0).mul(moveSpeed * dt));
    }
    if (keyCode == Keys.D) {
      camera.move(new Vec3(1, 0, 0).mul(moveSpeed * dt));
    }
    if (keyCode == Keys.W) {
      camera.move(new Vec3(0, 0, -1).mul(moveSpeed * dt));
    }
    if (keyCode == Keys.S) {
      camera.move(new Vec3(0, 0, 1).mul(moveSpeed * dt));
    }
    if (keyCode == Keys.Q) {
      camera.move(new Vec3(0, 1, 0).mul(moveSpeed * dt));
    }
    if (keyCode == Keys.E) {
      camera.move(new Vec3(0, -1, 0).mul(moveSpeed * dt));
    }
    if (keyCode == Keys.U) {
      camera.rotate(new Vec3(0, 0, 1), rotSpeed * dt);
    }
    if (keyCode == Keys.O) {
      camera.rotate(new Vec3(0, 0, -1), rotSpeed * dt);
    }
    if (keyCode == Keys.I) {
      camera.rotate(new Vec3(1, 0, 0), rotSpeed * dt);
    }
    if (keyCode == Keys.K) {
      camera.rotate(new Vec3(-1, 0, 0), rotSpeed * dt);
    }
    if (keyCode == Keys.J) {
      camera.rotate(new Vec3(0, 1, 0), rotSpeed * dt);
    }
    if (keyCode == Keys.L) {
      camera.rotate(new Vec3(0, 1, 0), -rotSpeed * dt);
    }
  }
}
