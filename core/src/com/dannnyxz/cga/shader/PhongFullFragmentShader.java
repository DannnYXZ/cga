package com.dannnyxz.cga.shader;

import static java.lang.Math.pow;

import com.dannnyxz.cga.FragmentShader;
import com.dannnyxz.cga.math.Util;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public class PhongFullFragmentShader implements FragmentShader {

  @Override
  public Vec4 execute(Map<String, Object> uniform, Map<String, Object> props) {
    Vec3 color = new Vec3();
    Vec3 lightDir = (Vec3) uniform.get("lightDir");
    Vec3 norm = (Vec3) props.get("vNorm");
    Vec3 ambientColor = new Vec3(0, 1, 1).mul(.1f);
    Vec3 diffuseColor = new Vec3(1, 1, 0).mul(lightDir.dot(norm)).mul(.1f);
    Vec3 reflected = Util.reflect(lightDir, norm);
    Vec3 reflectedColor = new Vec3(0, 1, 0)
        .mul((float) pow(reflected.dot(new Vec3(0, 0, 1)), 10) * 0.01f);
    color.add(ambientColor).add(diffuseColor).add(reflectedColor);
    return new Vec4(color, 1);
  }
}
