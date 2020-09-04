package com.dannnyxz.cga;

import static java.lang.Math.abs;
import static java.lang.Math.round;
import static org.apache.commons.lang3.math.NumberUtils.max;
import static org.apache.commons.lang3.math.NumberUtils.min;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.model.Face;
import com.dannnyxz.cga.model.Model;

public class Renderer {

  public void renderModel(Model model, Mat4 transform, Pixmap pixmap) {
    int i = 0;
    for (Face face : model.faces) {
      Vec3 v1 = model.vertices.get(face.vertices.get(0)).cp();
      Vec3 v2 = model.vertices.get(face.vertices.get(1)).cp();
      Vec3 v3 = model.vertices.get(face.vertices.get(2)).cp();
      //renderTriangle(v1.cp(), v2.cp(), v3.cp(), transform, pixmap);
      renderTriangleWire(v1.cp(), v2.cp(), v3.cp(), transform, pixmap);
      i++;
    }
  }

  public void renderTriangleWire(Vec3 v1, Vec3 v2, Vec3 v3, Mat4 transform, Pixmap pixmap) {
    v1.proj(transform);
    v2.proj(transform);
    v3.proj(transform);
    drawLine(v1, v2, pixmap);
    drawLine(v1, v3, pixmap);
    drawLine(v2, v3, pixmap);
  }

  public void renderTriangle(Vec3 v1, Vec3 v2, Vec3 v3, Mat4 transform, Pixmap pixmap) {
    v1.proj(transform);
    v2.proj(transform);
    v3.proj(transform);
    float minX = min(v1.x, v2.x, v3.x);
    float maxX = max(v1.x, v2.x, v3.x);
    float minY = min(v1.y, v2.y, v3.y);
    float maxY = max(v1.y, v2.y, v3.y);
    for (int i = round(minX); i <= maxX; i++) {
      for (int j = round(minY); j <= maxY; j++) {
        Vec3 brc = toBarycentric(new Vec3(i, j, 0), v1, v2, v3);

        if (brc.x < 0 || brc.y < 0 || brc.z < 0) continue;
        pixmap.drawPixel(i, j, new Color(1, 1, .3f, 1).toIntBits());
      }
    }
  }

  private Vec3 toBarycentric(Vec3 p, Vec3 v1, Vec3 v2, Vec3 v3) {
    Vec3 n = new Vec3(v3.x - v1.x, v2.x - v1.x, v1.x - p.x)
        .cross(new Vec3(v3.y - v1.y, v2.y - v1.y, v1.y - p.y));
    if (abs(n.z) > 1e-2)
      return new Vec3(1f - (n.x + n.y) / n.z, n.y / n.z, n.x / n.z);
    return new Vec3(-1, 1, 1);
  }

  private void drawLine(Vec3 d1, Vec3 d2, Pixmap pixmap) {
    float x1 = d1.x;
    float y1 = d1.y;
    float x2 = d2.x;
    float y2 = d2.y;

    int L = (int) Math.max(abs(x1 - x2), abs(y1 - y2));
    float x = x1;
    float y = y1;
    for (int i = 0; i < L; i++) {
      pixmap.drawPixel((int) x, (int) y, new Color(1, .1f, .1f, .5f).toIntBits());
      x += (x2 - x1) / L;
      y += (y2 - y1) / L;
    }
  }
}
