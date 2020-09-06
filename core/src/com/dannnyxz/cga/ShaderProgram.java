package com.dannnyxz.cga;

import static com.dannnyxz.cga.math.Vec3.toBarycentric;
import static java.lang.Math.abs;
import static java.lang.Math.random;
import static java.lang.Math.round;
import static org.apache.commons.lang3.math.NumberUtils.max;
import static org.apache.commons.lang3.math.NumberUtils.min;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import com.dannnyxz.cga.model.Polygon;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class ShaderProgram {

  private final Map<String, Object> uniforms = new HashMap<>();

  public Map<String, Object> uniforms() {
    return uniforms;
  }

  private Float[][] zBuffer;
  private static AtomicInteger[][] lock;
  AtomicInteger cnt = new AtomicInteger(0);

  ExecutorService pool = Executors.newCachedThreadPool();

  public void enableZBuffer(int w, int h) {
    zBuffer = new Float[w][h];
    lock = new AtomicInteger[w][h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        zBuffer[i][j] = 1e30f;
        lock[i][j] = new AtomicInteger(0);
      }
    }
  }

  public void clearZBuffer() {
    for (int i = 0; i < zBuffer.length; i++) {
      for (int j = 0; j < zBuffer[0].length; j++) {
        zBuffer[i][j] = 1e30f;
      }
    }
  }

  VertexShader vertexShader = new VertexShader() {
    @Override
    public Vec4 execute(Vec4 vertex, Map<String, Object> uniforms, Map<String, Object> props) {
      Mat4 transform = (Mat4) uniforms.get("transform");
      Vec4 transformed = vertex.mul(transform);
      return transformed;
    }
  };

  FragmentShader fragmentShader = new FragmentShader() {
    @Override
    public Vec4 execute(Map<String, Object> in, Map<String, Object> props) {
      float intensity = ((Vec3) props.get("norm")).dot((Vec3) uniforms.get("lightSource"));
//      intensity = .2f;
      Vec3 brc = (Vec3) props.get("brc");
      float d = min(brc.x, brc.y, brc.z);
      Vec4 col = (Vec4) props.get("color");
      return col;
//      return new Vec4(0, 1, 0, 1);
//      return new Vec4(intensity, intensity, intensity, 1);
    }
  };

  public void renderTriangle(Vec3 v1, Vec3 v2, Vec3 v3, Map<String, Object> vertexProps,
      Pixmap pixmap) {
    int minX = round(min(v1.x, v2.x, v3.x));
    int maxX = round(max(v1.x, v2.x, v3.x));
    int minY = round(min(v1.y, v2.y, v3.y));
    int maxY = round(max(v1.y, v2.y, v3.y));
    for (int i = minX; i <= maxX; i++) {
      for (int j = minY; j <= maxY; j++) {
        if (i >= pixmap.getWidth() || i < 0 || j >= pixmap.getHeight() || j < 0) continue;
        Vec3 brc = toBarycentric(new Vec3(i, j, 0), v1, v2, v3);
        if (brc.x < 0 || brc.y < 0 || brc.z < 0) continue;
        Vec3 frPos = v1.cp().mul(brc.x).add(v2.cp().mul(brc.y)).add(v3.cp().mul(brc.z));
        if (zBuffer != null) {
//          synchronized (zBuffer) {
          synchronized (lock[i][j]) {
            if (frPos.z < zBuffer[i][j]) {
              zBuffer[i][j] = frPos.z;
              vertexProps.put("frPos", frPos);
              vertexProps.put("brc", brc);
              // TODO: interpolate all props
              Vec4 color = fragmentShader.execute(uniforms, vertexProps);
//              float x = zBuffer[i][j];
//              x *= x*x*.1f;
              pixmap.drawPixel(i, j, new Color(color.w, color.x, color.y, color.z).toIntBits());
//              pixmap.drawPixel(i, j, new Color(color.w, x, x, x).toIntBits());
//            }
            }
          }
        }
      }
    }
  }

  boolean isBetween(float a, float min, float max) {
    return a >= min && a <= max;
  }

  boolean clip(Vec4 v) {
    if (!isBetween(v.x, -v.w, v.w)) return true;
    if (!isBetween(v.y, -v.w, v.w)) return true;
    if (!isBetween(v.z, -v.w, v.w)) return true;
    return false;
  }

  public void drawFace(Polygon polygon, Pixmap pixmap) {
    Mat4 view = (Mat4) uniforms.get("view");
    Mat4 model = (Mat4) uniforms.get("model");
    Mat4 proj = (Mat4) uniforms.get("proj");
    Mat4 screen = (Mat4) uniforms.get("screen");
    Map<String, Object> props = new HashMap<>();
//    Vec4 norm = new Vec4(polygon.norm, 0).mul(model);
    props.put("norm", polygon.norm);
    props.put("color",
        new Vec4((float) random(), (float) random(), (float) random(), 1));
    Vec4 v1 = vertexShader.execute(new Vec4(polygon.vertices.get(0), 1), uniforms, props);
    Vec4 v2 = vertexShader.execute(new Vec4(polygon.vertices.get(1), 1), uniforms, props);
    Vec4 v3 = vertexShader.execute(new Vec4(polygon.vertices.get(2), 1), uniforms, props);

//    if (v1.z < .301 || v2.z < .301 || v3.z < 0.301) return;
//
//    v1.mul(1 / v1.w);
//    v2.mul(1 / v2.w);
//    v3.mul(1 / v3.w);

//    if (clip(v1) || clip(v2) || clip(v3)) return;
    if (clip(v1) || clip(v2) || clip(v3)) return;

    v1.mul(1 / v1.w).mul(screen);
    v2.mul(1 / v2.w).mul(screen);
    v3.mul(1 / v3.w).mul(screen);

    renderTriangle(new Vec3(v1), new Vec3(v2), new Vec3(v3), props, pixmap);
//    renderTriangleWire(new Vec3(v1), new Vec3(v2), new Vec3(v3), pixmap);
  }

  public void drawFaces(List<Polygon> polygons, Pixmap buffer) {
    polygons.parallelStream().forEach(polygon -> drawFace(polygon, buffer));
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
