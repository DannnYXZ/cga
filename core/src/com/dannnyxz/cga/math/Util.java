package com.dannnyxz.cga.math;

public class Util {

  public static Vec3 reflect(Vec3 v, Vec3 n) {
    return v.cp().sub(n.cp().mul(2 * v.dot(n)));
  }
}
