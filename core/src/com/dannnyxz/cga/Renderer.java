package com.dannnyxz.cga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.model.Face;
import com.dannnyxz.cga.model.Model;

public class Renderer {

  public void renderTriangle(Model model, Mat4 transform) {
    Pixmap pixmap = new Pixmap(
        Gdx.graphics.getWidth(),
        Gdx.graphics.getHeight(),
        Pixmap.Format.RGBA8888
    );
    for (Face face : model.faces) {
      Vec3 v1 = model.vertices.get(face.vertices.get(0)).cp();
      Vec3 v2 = model.vertices.get(face.vertices.get(1)).cp();
      Vec3 v3 = model.vertices.get(face.vertices.get(2)).cp();
      renderTriangle(v1, v2, v3, transform, pixmap);
    }
    SpriteBatch spriteBatch = new SpriteBatch();
    spriteBatch.begin();
    spriteBatch.draw(new Texture(pixmap), 0, 0);
    spriteBatch.end();
  }

  public void renderTriangle(Vec3 v1, Vec3 v2, Vec3 v3, Mat4 transform, Pixmap pixmap) {
    v1.proj(transform);
    v2.proj(transform);
    v3.proj(transform);
    drawLine(v1, v2, pixmap);
    drawLine(v1, v3, pixmap);
    drawLine(v2, v3, pixmap);
  }


  private void drawLine(Vec3 d1, Vec3 d2, Pixmap pixmap) {
    float x1 = d1.x;
    float y1 = d1.y;
    float x2 = d2.x;
    float y2 = d2.y;

    int L = (int) Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    float x = x1;
    float y = y1;
    for (int i = 0; i < L; i++) {
      pixmap.drawPixel((int) x, (int) y, new Color(1, 0, 0, 1).toIntBits());
      x += (x2 - x1) / L;
      y += (y2 - y1) / L;
    }
  }
}
