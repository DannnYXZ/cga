package com.dannnyxz.cga.shader;

import com.dannnyxz.cga.FragmentShader;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public class LambertFragmentShader implements FragmentShader {

  @Override
  public Vec4 execute(Map<String, Object> uniform, Map<String, Object> props) {
    Vec3 lightPos = (Vec3) uniform.get("lightDir");
    float intensity = ((Vec3) props.get("norm")).dot(lightPos.norm());
    return new Vec4(intensity, intensity, intensity, 1);
  }
}
