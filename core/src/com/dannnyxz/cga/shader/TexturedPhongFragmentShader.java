package com.dannnyxz.cga.shader;

import static com.dannnyxz.cga.Sampler2D.texture;
import static com.dannnyxz.cga.math.Util.reflect;
import static java.lang.Math.max;
import static java.lang.Math.pow;

import com.badlogic.gdx.graphics.Pixmap;
import com.dannnyxz.cga.FragmentShader;
import com.dannnyxz.cga.math.Mat4;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.math.Vec4;

import java.util.Map;

public class TexturedPhongFragmentShader implements FragmentShader {

  @Override
  public Vec4 execute(Map<String, Object> uniform, Map<String, Object> props) {
    Vec3 color = new Vec3();
    Vec3 pos = (Vec3) props.get("pos");
//        Vec3 lightDir = (Vec3) uniform.get("lightDir");
    Mat4 view = (Mat4) uniform.get("view");
    Vec3 lightDir = ((Vec3) uniform.get("lightDir")).cp().mul(view).sub(pos.cp()).norm();
    Vec3 uv = (Vec3) props.get("uv");
    Pixmap normalMap = (Pixmap) uniform.get("normalMap");
    Pixmap diffuseMap = (Pixmap) uniform.get("diffuseMap");
    Pixmap specularMap = (Pixmap) uniform.get("specularMap");
    Vec4 norm = texture(normalMap, uv).mul(2).sub(new Vec4(1, 1, 1, 0));
    Vec3 diffuseColor = new Vec3(texture(diffuseMap, uv));
    Vec3 ambientColor = diffuseColor.cp().mul(.6f);
    Vec3 reflected = reflect(lightDir, new Vec3(norm));
    float ks = texture(specularMap, uv).x;
    Vec3 reflectedColor = new Vec3(1, 1, 1)
        .mul((float) pow(max(0, reflected.dot(pos.cp().norm())), 128) * ks);
    color.add(diffuseColor);//.add(reflectedColor).add(ambientColor);
    return new Vec4(color, 1);
  }
}
