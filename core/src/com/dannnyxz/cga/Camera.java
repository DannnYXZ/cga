package com.dannnyxz.cga;

import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Quaternion;
import com.dannnyxz.cga.math.Vec3;


public class Camera{

  public Vec3 pos;
  public Quaternion orient;

  public Camera(Vec3 pos, Vec3 v, float ang) {
    this.pos = pos;
    this.orient = new Quaternion(v, ang);
  }

  public Mat4 getView() {
    return orient.copy().conjugate().toMatrix().translate(pos.cp().neg());
  }

  public void move(Vec3 v) {
    pos.add(orient.copy().rot(v.cp()));
  }

  public void rotate(Vec3 v, float ang) {
    orient.mul(new Quaternion(v.cp().norm(), ang));
  }
}
