package com.dannnyxz.cga.shader;

import com.dannnyxz.cga.VertexShader;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public class BasicVertexShader implements VertexShader {

  @Override
  public Vec4 execute(Vec4 vertex, Map<String, Object> uniforms, Map<String, Object> props) {
    Mat4 view = (Mat4) uniforms.get("view");
    props.put("pos", new Vec3(vertex).mul(view));
    Mat4 transform = (Mat4) uniforms.get("transform");
    return vertex.cp().mul(transform);
  }
}
