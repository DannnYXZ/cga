package com.dannnyxz.cga.shader;

import static java.lang.Math.max;
import static java.lang.Math.pow;

import com.dannnyxz.cga.FragmentShader;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Util;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public class PhongFragmentShader implements FragmentShader {

  @Override
  public Vec4 execute(Map<String, Object> uniform, Map<String, Object> props) {
    Vec3 color = new Vec3();
    Vec3 pos = (Vec3) props.get("pos");
    Mat4 view = (Mat4) uniform.get("view");
    Vec3 lightDir = ((Vec3) uniform.get("lightDir")).cp().mul(view).sub(pos.cp()).norm();
    //Vec3 lightDir = (Vec3) uniform.get("lightDir");
    Vec3 norm = (Vec3) props.get("vNorm");
    Vec3 ambientColor = new Vec3(1, 1, 0).mul(.2f);
    Vec3 diffuseColor = new Vec3(1, 1, 0).mul(max(0, lightDir.dot(norm))).mul(.8f);
    Vec3 reflected = Util.reflect(lightDir, norm);
    Vec3 reflectedColor = new Vec3(1, 1, 1)
        .mul((float) pow(max(0, reflected.dot(pos.cp().norm())), 128));
    color.add(ambientColor).add(diffuseColor).add(reflectedColor);
    return new Vec4(color, 1);
  }
}
