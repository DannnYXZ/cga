package com.dannnyxz.cga.math;

import static java.lang.Math.sqrt;

public class Vec4 {

  public float x;
  public float y;
  public float z;
  public float w;

  public Vec4() {
  }

  public Vec4(float x, float y, float z, float w) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.w = w;
  }

  public Vec4(Vec3 v, float w) {
    this.x = v.x;
    this.y = v.y;
    this.z = v.z;
    this.w = w;
  }

  public float dot(Vec4 b) {
    return x * b.x + y * b.y + z * b.z;
  }

  public Vec4 cross(Vec4 b) {
    return new Vec4(y * b.z - z * b.y, z * b.x - x * b.z, x * b.y - y * b.x, w);
  }


  public Vec4 cp() {
    return new Vec4(x, y, z, w);
  }

  public Vec4 add(Vec4 b) {
    x += b.x;
    y += b.y;
    z += b.z;
    w += b.w;
    return this;
  }

  public Vec4 sub(Vec4 b) {
    x -= b.x;
    y -= b.y;
    z -= b.z;
    w -= b.w;
    return this;
  }

  public Vec4 mul(float k) {
    x *= k;
    y *= k;
    z *= k;
    w *= k;
    return this;
  }

  public Vec4 mul(Mat4 b) {
    final float m[][] = b.mat;
    float _x = x * m[0][0] + y * m[0][1] + z * m[0][2] + w * m[0][3];
    float _y = x * m[1][0] + y * m[1][1] + z * m[1][2] + w * m[1][3];
    float _z = x * m[2][0] + y * m[2][1] + z * m[2][2] + w * m[2][3];
    float _w = x * m[3][0] + y * m[3][1] + z * m[3][2] + w * m[3][3];
    x = _x;
    y = _y;
    z = _z;
    w = _w;
    return this;
  }

  public Vec4 proj(Mat4 b) {
    float[][] m = b.mat;
    return this.mul(b);
  }

  public Vec4 neg() {
    x = -x;
    y = -y;
    z = -z;
    return this;
  }

  public float len() {
    return (float) sqrt(x * x + y * y + z * z);
  }

  public Vec4 norm() {
    float l = this.len();
    if (l == 0f || l == 1f) return this;
    return this.mul(1 / l);
  }
}
