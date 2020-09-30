package com.dannnyxz.cga;

import static java.lang.Math.round;

import com.badlogic.gdx.graphics.Pixmap;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;

public class Sampler2D {

  public static Vec4 texture(Pixmap tex, Vec3 uv) {
    int color = tex.getPixel(round(tex.getWidth() * uv.x), round(tex.getHeight() * (1 - uv.y)));
    return new Vec4((color >> 24) & 0xFF, (color >> 16) & 0xFF, (color >> 8) & 0xFF, color & 0xFF)
        .mul(1f / 255);
  }
}
