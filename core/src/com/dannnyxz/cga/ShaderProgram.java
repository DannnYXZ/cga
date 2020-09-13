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
import com.dannnyxz.cga.shader.BasicVertexShader;
import com.dannnyxz.cga.shader.ExplosiveVertexShader;
import com.dannnyxz.cga.shader.PhongFullFragmentShader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;


public class ShaderProgram {

  public enum DrawMode {LINES, FACES}

  public Map<String, Object> uniforms() {
    return uniforms;
  }

  private DrawMode drawMode = DrawMode.FACES;
  private final Map<String, Object> uniforms = new HashMap<>();
  private Float[][] zBuffer;
  private static AtomicInteger[][] lock;
  private boolean isBackFaceCullingEnabled = true;

  public ShaderProgram setDrawMode(DrawMode drawMode) {
    this.drawMode = drawMode;
    return this;
  }

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

  public void setBackFaceCullingEnabled(boolean enabled) {
    isBackFaceCullingEnabled = enabled;
  }

  public void clearZBuffer() {
    for (int i = 0; i < zBuffer.length; i++) {
      for (int j = 0; j < zBuffer[0].length; j++) {
        zBuffer[i][j] = 1e30f;
      }
    }
  }

  VertexShader vertexShader = new BasicVertexShader();
//    VertexShader vertexShader = new ExplosiveVertexShader();
  //  FragmentShader fragmentShader = new WireframeFragmentShader();
  //  FragmentShader fragmentShader = new LambertFragmentShader();
//  FragmentShader fragmentShader = new PhongFragmentShader();
  FragmentShader fragmentShader = new PhongFullFragmentShader();

  private void lerp(Vec3 brc, final List<Map<String, Object>> vVariables,
      Map<String, Object> fVariablesToFill) {
    for (Entry<String, Object> kv : vVariables.get(0).entrySet()) {
      String name = kv.getKey();
      Object value = kv.getValue();
      if (name.startsWith("flat_")) {
        fVariablesToFill.put(name.replace("flat_", ""), value);
      }
      if (value instanceof Vec3) {
        fVariablesToFill.put(name, ((Vec3) vVariables.get(0).get(name)).cp().mul(brc.x)
            .add(((Vec3) vVariables.get(1).get(name)).cp().mul(brc.y))
            .add(((Vec3) vVariables.get(2).get(name)).cp().mul(brc.z)));
      }
      if (value instanceof Vec4) {
        fVariablesToFill.put(name, ((Vec4) vVariables.get(0).get(name)).cp().mul(brc.x)
            .add(((Vec4) vVariables.get(1).get(name)).cp().mul(brc.y))
            .add(((Vec4) vVariables.get(2).get(name)).cp().mul(brc.z)));
      }
    }
  }

  public void renderTriangle(Vec3 v1, Vec3 v2, Vec3 v3, List<Map<String, Object>> vertexProps,
      Pixmap pixmap) {
    int minX = round(min(v1.x, v2.x, v3.x));
    int maxX = round(max(v1.x, v2.x, v3.x));
    int minY = round(min(v1.y, v2.y, v3.y));
    int maxY = round(max(v1.y, v2.y, v3.y));
    Map<String, Object> fProps = new HashMap<>(); // for speed
    for (int i = minX; i <= maxX; i++) {
      for (int j = minY; j <= maxY; j++) {
        if (i >= pixmap.getWidth() || i < 0 || j >= pixmap.getHeight() || j < 0) continue;
        Vec3 brc = toBarycentric(new Vec3(i, j, 0), v1, v2, v3);
        if (brc.x < 0 || brc.y < 0 || brc.z < 0) continue;
        Vec3 fPos = v1.cp().mul(brc.x).add(v2.cp().mul(brc.y)).add(v3.cp().mul(brc.z));
        if (zBuffer != null) {
          synchronized (lock[i][j]) {
            if (fPos.z < zBuffer[i][j]) {
              lerp(brc, vertexProps, fProps);
              fProps.put("brc", brc);
              Vec4 color = fragmentShader.execute(uniforms, fProps);
              if (color == null) continue; // discard
              zBuffer[i][j] = fPos.z;
              pixmap.drawPixel(i, j, new Color(color.w, color.x, color.y, color.z).toIntBits());
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

  boolean clipZ(Vec4 v) {
    if (!isBetween(v.z, -v.w, v.w)) return true;
    return false;
  }


  public void drawFace(Polygon polygon, Pixmap pixmap) {
    List<Vec4> vertices = Arrays.asList(
        new Vec4(polygon.vertices.get(0), 1),
        new Vec4(polygon.vertices.get(1), 1),
        new Vec4(polygon.vertices.get(2), 1)
    );
    Mat4 view = (Mat4) uniforms.get("view");
    Mat4 model = (Mat4) uniforms.get("model");
    Mat4 proj = (Mat4) uniforms.get("proj");
    Mat4 screen = (Mat4) uniforms.get("screen");

    List<Map<String, Object>> vOutProps = new ArrayList<>();
    List<Vec4> _vertices = new ArrayList<>();
    for (int i = 0; i < vertices.size(); i++) {
      Map<String, Object> _inOutProps = new HashMap<>();
      _inOutProps.put("norm", polygon.norm);
      _inOutProps.put("vNorm", polygon.norms.get(i));
      Vec4 _v = vertexShader.execute(vertices.get(i), uniforms, _inOutProps);
      vOutProps.add(_inOutProps);
      _vertices.add(_v);
    }

    if (clip(_vertices.get(0))
        || clip(_vertices.get(1))
        || clip(_vertices.get(2))) return;
    _vertices.forEach(v -> v.mul(1 / v.w).mul(screen));

    if (isBackFaceCullingEnabled) {
      Vec4 screenSpaceNorm =
          _vertices.get(0).cp().sub(_vertices.get(1))
              .cross(_vertices.get(2).cp().sub(_vertices.get(1))).norm();
      if (screenSpaceNorm.dot(new Vec4(0, 0, 1, 0)) < 0) {
        return;
      }
    }

    if (drawMode == DrawMode.LINES) {
      renderTriangleWire(new Vec3(_vertices.get(0)),
          new Vec3(_vertices.get(1)),
          new Vec3(_vertices.get(2)),
          pixmap);
    }
    if (drawMode == DrawMode.FACES) {
      renderTriangle(new Vec3(_vertices.get(0)),
          new Vec3(_vertices.get(1)),
          new Vec3(_vertices.get(2)), vOutProps, pixmap);
    }
  }


  public void drawFaces(List<Polygon> polygons, Pixmap buffer) {
    polygons.parallelStream().forEach(polygon -> drawFace(polygon, buffer));
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
