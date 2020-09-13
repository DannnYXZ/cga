package com.dannnyxz.cga.math;

import static com.badlogic.gdx.math.MathUtils.clamp;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class Quaternion {

  public float x;
  public float y;
  public float z;
  public float w;

  public Quaternion(float x, float y, float z, float w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public Quaternion(Vec3 v, float ang) {
    v.norm();
    float hsin = (float) sin(ang * .5);
    x = hsin * v.x;
    y = hsin * v.y;
    z = hsin * v.z;
    w = (float) cos(ang * .5);
  }

  public static Quaternion identity() {
    return new Quaternion(0, 0, 0, 1);
  }

  public Quaternion copy() {
    return new Quaternion(x, y, z, w);
  }

  public float len() {
    return (float) sqrt(x * x + y * y + z * z + w * w);
  }

  public float angle() {
    return (float) acos(clamp(w, -1, 1));
  }

  public Quaternion norm() {
    float length = this.len();
    x /= length;
    x /= length;
    x /= length;
    x /= length;
    return this;
  }

  public Quaternion conjugate() {
    x = -x;
    y = -y;
    z = -z;
    return this;
  }

  public Quaternion mul(Quaternion r) {
    float _w = w * r.w - x * r.x - y * r.y - z * r.z;
    float _x = x * r.w + w * r.x - z * r.y + y * r.z;
    float _y = y * r.w + z * r.x + w * r.y - x * r.z;
    float _z = z * r.w - y * r.x + x * r.y + w * r.z;
    x = _x;
    y = _y;
    z = _z;
    w = _w;
    return this;
  }

  public Quaternion mul(Vec3 r) {
    float _w = -x * r.x - y * r.y - z * r.z;
    float _x = w * r.x - z * r.y + y * r.z;
    float _y = z * r.x + w * r.y - x * r.z;
    float _z = -y * r.x + x * r.y + w * r.z;
    x = _x;
    y = _y;
    z = _z;
    w = _w;
    return this;
  }

  public Vec3 rot(Vec3 v) {
    Quaternion q = this.copy().mul(v).mul(copy().conjugate());
    v.x = q.x;
    v.y = q.y;
    v.z = q.z;
    return v;
  }

  public Mat4 toMatrix() {
    return new Mat4(new float[][]{
        {1 - 2 * y * y - 2 * z * z, 2 * x * y - 2 * z * w, 2 * x * z + 2 * y * w, 0},
        {2 * x * y + 2 * z * w, 1 - 2 * x * x - 2 * z * z, 2 * y * z - 2 * x * w, 0},
        {2 * x * z - 2 * y * w, 2 * y * z + 2 * x * w, 1 - 2 * x * x - 2 * y * y, 0},
        {0, 0, 0, 1}});
  }
}
