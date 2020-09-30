package com.dannnyxz.cga.shader;

import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static org.apache.commons.lang3.math.NumberUtils.min;

import com.dannnyxz.cga.FragmentShader;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public class WireframeFragmentShader implements FragmentShader {

  @Override
  public Vec4 execute(Map<String, Object> uniform, Map<String, Object> props) {
    Vec3 brc = (Vec3) props.get("brc");
    float d = min(brc.x, brc.y, brc.z);
    float time = (float) uniform.get("time");
    if (d > .1*abs(sin(time))) return null;
    Vec4 col = new Vec4(0, 0, 0, 1);
    return col;
  }
}
