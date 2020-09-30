package com.dannnyxz.cga.shader;

import static com.badlogic.gdx.math.MathUtils.sin;
import static java.lang.Float.max;

import com.dannnyxz.cga.VertexShader;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public class ExplosiveVertexShader implements VertexShader {

  @Override
  public Vec4 execute(Vec4 vertex, Map<String, Object> uniforms, Map<String, Object> props) {
    Vec4 norm = new Vec4((Vec3) props.get("norm"), 0);
    float time = (Float) uniforms.get("time");
    vertex.add(norm.cp().mul(3 * max(0, (sin(time)))));
    Mat4 transform = (Mat4) uniforms.get("transform");
    return vertex.mul(transform);
  }
}
