package com.dannnyxz.cga.shader;

import com.badlogic.gdx.graphics.Pixmap;
import com.dannnyxz.cga.VertexShader;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public class TexturedVertexShader implements VertexShader {

  @Override
  public Vec4 execute(Vec4 vertex, Map<String, Object> uniform, Map<String, Object> props) {
    Pixmap normalMap = (Pixmap) uniform.get("normalMap");
    Mat4 transform = (Mat4) uniform.get("transform");
    props.put("pos", vertex);
    return vertex.mul(transform);
  }
}
