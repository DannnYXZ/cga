package com.dannnyxz.cga.math;

import static java.lang.Math.tan;

public class Mat4 {

  public float[][] mat;

  public Mat4() {
    mat = new float[][]{
        {1, 0, 0, 0},
        {0, 1, 0, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 1}
    };
  }

  public Mat4(float mat[][]) {
    this.mat = mat;
  }

  public Mat4 mul(Mat4 b) {
    float[][] tmp = new float[4][4];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        for (int k = 0; k < 4; k++) {
          tmp[i][j] += this.mat[i][k] * b.mat[k][j];
        }
      }
    }
    this.mat = tmp;
    return this;
  }

  public Mat4 translate(Vec3 v) {
    return this.mul(new Mat4(new float[][]{
        {1, 0, 0, v.x},
        {0, 1, 0, v.y},
        {0, 0, 1, v.z},
        {0, 0, 0, 1}
    }));
  }

  public static Mat4 projection(float zNear, float z_far, float aspect, float fov) {
    return new Mat4(new float[][]{
        {1 / (aspect * (float) tan(fov / 2f)), 0, 0, 0},
        {0, 1 / (float) tan(fov / 2), 0, 0},
        {0, 0, z_far / (zNear - z_far), zNear * z_far / (zNear - z_far)},
        {0, 0, -1, 0}});
  }

  public Vec3 mulProj(Vec3 b) {
    return b.mul(this);
  }

  public static Mat4 lookAt(Vec3 eye, Vec3 point) {
    Vec3 f = eye.cp().add(point.cp().neg()).norm();
    Vec3 r = new Vec3(0, 1, 0).cross(f).norm();
    Vec3 u = f.cp().cross(r);
    return new Mat4(new float[][]{
        {r.x, r.y, r.z, 0},
        {u.x, u.y, u.z, 0},
        {f.x, f.y, f.z, 0},
        {0, 0, 0, 1}}).translate(eye.cp().neg());
  }

  public static Mat4 viewport(float w, float h) {
    return new Mat4(new float[][]{
        {w / 2, 0, 0, w / 2},
        {0, -h / 2, 0, h / 2},
        {0, 0, 1, 0},
        {0, 0, 0, 1}});
  }
}
