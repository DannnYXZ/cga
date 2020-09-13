package com.dannnyxz.cga.shader;

import static org.apache.commons.lang3.math.NumberUtils.min;

import com.dannnyxz.cga.FragmentShader;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public class WireframeFragmentShader implements FragmentShader {

  @Override
  public Vec4 execute(Map<String, Object> uniform, Map<String, Object> props) {
    float intensity = ((Vec3) props.get("norm")).dot((Vec3) uniform.get("lightSource"));
//      intensity = .2f;
    Vec3 brc = (Vec3) props.get("brc");
    float d = min(brc.x, brc.y, brc.z);
    if (d > .05) return null;
    Vec4 col = (Vec4) props.get("color");
    return col;
//      return new Vec4(0, 1, 0, 1);
//      return new Vec4(intensity, intensity, intensity, 1);
  }
}
