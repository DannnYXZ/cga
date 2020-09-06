package com.dannnyxz.cga;

import com.dannnyxz.cga.math.Vec4;
import java.util.Map;

public interface FragmentShader {

  Vec4 execute(Map<String, Object> uniform, Map<String, Object> props);
}
