package com.dannnyxz.cga.math;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Vec3 {

  public float x;
  public float y;
  public float z;

  public Vec3() {
  }

  public Vec3(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vec3(Vec4 v) {
    this.x = v.x;
    this.y = v.y;
    this.z = v.z;
  }

  public float dot(Vec3 b) {
    return x * b.x + y * b.y + z * b.z;
  }

  public Vec3 cross(Vec3 b) {
    return new Vec3(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x);
  }

  public Vec3 cp() {
    return new Vec3(x, y, z);
  }

  public Vec3 add(Vec3 b) {
    x += b.x;
    y += b.y;
    z += b.z;
    return this;
  }

  public Vec3 sub(Vec3 b) {
    x -= b.x;
    y -= b.y;
    z -= b.z;
    return this;
  }

  public Vec3 mul(Vec3 v) {
    x *= v.x;
    y *= v.y;
    z *= v.z;
    return this;
  }

  public Vec3 mul(float k) {
    x *= k;
    y *= k;
    z *= k;
    return this;
  }

  public Vec3 mul(Mat4 b) {
    final float m[][] = b.mat;
    float _x = x * m[0][0] + y * m[0][1] + z * m[0][2] + m[0][3];
    float _y = x * m[1][0] + y * m[1][1] + z * m[1][2] + m[1][3];
    float _z = x * m[2][0] + y * m[2][1] + z * m[2][2] + m[2][3];
    x = _x;
    y = _y;
    z = _z;
    return this;
  }

  public Vec3 proj(Mat4 b) {
    float[][] m = b.mat;
    float w = x * m[3][0] + y * m[3][1] + z * m[3][2] + m[3][3];
    return this.mul(b).mul(1 / w);
  }

  public Vec3 neg() {
    x = -x;
    y = -y;
    z = -z;
    return this;
  }

  public float len() {
    return (float) sqrt(x * x + y * y + z * z);
  }

  public Vec3 norm() {
    float l = this.len();
    if (l == 0f || l == 1f) return this;
    return this.mul(1 / l);
  }

  public static Vec3 toBarycentric(Vec3 p, Vec3 v1, Vec3 v2, Vec3 v3) {
    Vec3 n = new Vec3(v3.x - v1.x, v2.x - v1.x, v1.x - p.x)
        .cross(new Vec3(v3.y - v1.y, v2.y - v1.y, v1.y - p.y));
    if (abs(n.z) > 1e-3)
      return new Vec3(1f - (n.x + n.y) / n.z, n.y / n.z, n.x / n.z);
    return new Vec3(-1, 1, 1);
  }
}
