package com.dannnyxz.cga;

import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public interface VertexShader {

  Vec4 execute(Vec4 vertex, Map<String, Object> uniforms);
}
