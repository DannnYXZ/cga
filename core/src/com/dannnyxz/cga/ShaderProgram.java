package com.dannnyxz.cga;

import static com.dannnyxz.cga.math.Vec3.toBarycentric;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import static org.apache.commons.lang3.math.NumberUtils.max;
import static org.apache.commons.lang3.math.NumberUtils.min;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import com.dannnyxz.cga.model.Polygon;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ShaderProgram {

  private final Map<String, Object> uniforms = new HashMap<>();

  public Map<String, Object> uniforms() {
    return uniforms;
  }

  private Float[][] zBuffer;
  private Byte[][] lock;
  AtomicInteger cnt = new AtomicInteger(0);

  public void enableZBuffer(int w, int h) {
    zBuffer = new Float[w][h];
    lock = new Byte[w][h];
    for (int i = 0; i < w; i++) {
      Arrays.fill(zBuffer[i], -Float.MAX_VALUE);
      for (int j = 0; j < h; j++) {
        lock[i][j] = Byte.parseByte("0");
      }
    }
  }

  public void clearZBuffer() {
    for (int i = 0; i < zBuffer.length; i++) {
      Arrays.fill(zBuffer[i], -Float.MAX_VALUE);
    }
  }

  VertexShader vertexShader = new VertexShader() {
    @Override
    public Vec4 execute(Vec4 vertex, Map<String, Object> in) {
      Mat4 transform = (Mat4) in.get("transform");
      Vec4 transformed = vertex.mul(transform);
      return transformed;
    }
  };

  FragmentShader fragmentShader = new FragmentShader() {
    @Override
    public Vec4 execute(Map<String, Object> in) {
      float intensity = ((Vec3) in.get("norm")).dot((Vec3) in.get("lightSource"));
//      return new Vec4(0, 1, 0, 1);
      return new Vec4(intensity, intensity, intensity, 1);
    }
  };

  public void renderTriangle(Vec3 v1, Vec3 v2, Vec3 v3, Pixmap pixmap) {
    float minX = min(v1.x, v2.x, v3.x);
    float maxX = max(v1.x, v2.x, v3.x);
    float minY = min(v1.y, v2.y, v3.y);
    float maxY = max(v1.y, v2.y, v3.y);
    for (int i = round(minX); i <= maxX; i++) {
      for (int j = round(minY); j <= maxY; j++) {
        if (i >= pixmap.getWidth() || i < 0 || j >= pixmap.getHeight() || j < 0) continue;
        Vec3 brc = toBarycentric(new Vec3(i, j, 0), v1, v2, v3);
        if (brc.x < 0 || brc.y < 0 || brc.z < 0) continue;
        Vec3 frPos = v1.cp().mul(brc.x).add(v2.cp().mul(brc.y)).add(v3.cp().mul(brc.z));
        if (zBuffer != null) {
//          synchronized (zBuffer) {

          cnt.incrementAndGet();
          if (frPos.z > zBuffer[i][j]) {
            zBuffer[i][j] = frPos.z;
            uniforms.put("frPos", frPos);
            Vec4 color = fragmentShader.execute(uniforms);
            pixmap.drawPixel(i, j, new Color(color.w, color.x, color.y, color.z).toIntBits());
          }
//          }
        }
      }
    }
  }

  public void drawFace(Polygon polygon, Pixmap pixmap) {
    uniforms.put("norm", polygon.norm);
    Vec4 v1 = vertexShader.execute(new Vec4(polygon.vertices.get(0), 1), uniforms);
    Vec4 v2 = vertexShader.execute(new Vec4(polygon.vertices.get(1), 1), uniforms);
    Vec4 v3 = vertexShader.execute(new Vec4(polygon.vertices.get(2), 1), uniforms);
    v1.mul(1 / v1.w);
    v2.mul(1 / v2.w);
    v3.mul(1 / v3.w);
    renderTriangle(new Vec3(v1.x, v1.y, v1.z), new Vec3(v2.x, v2.y, v2.z),
        new Vec3(v3.x, v3.y, v3.z), pixmap);
  }

  public void drawFaces(List<Polygon> polygons, Pixmap buffer) {
    polygons.parallelStream().forEach(x -> drawFace(x, buffer));
//    polygons.forEach(x -> drawFace(x, buffer));
    clearZBuffer();
  }

  public void renderTriangleWire(Vec3 v1, Vec3 v2, Vec3 v3, Pixmap pixmap) {
    drawLine(v1, v2, pixmap);
    drawLine(v1, v3, pixmap);
    drawLine(v2, v3, pixmap);
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
