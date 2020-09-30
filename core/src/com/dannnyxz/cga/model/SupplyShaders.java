package com.dannnyxz.cga.model;

import com.badlogic.gdx.Input.Keys;
import com.dannnyxz.cga.ShaderProgram;
import com.dannnyxz.cga.shader.LambertFragmentShader;
import com.dannnyxz.cga.shader.PhongLigtingFragmentShader;
import com.dannnyxz.cga.shader.PhongFragmentShader;
import com.dannnyxz.cga.shader.TexturedPhongFragmentShader;
import com.dannnyxz.cga.shader.WireframeFragmentShader;

public class SupplyShaders implements EventListener {

  public SupplyShaders(ShaderProgram shaderProgram) {
    this.shaderProgram = shaderProgram;
  }

  ShaderProgram shaderProgram;

  @Override
  public void update(Object data) {
    Integer keyCode = (Integer) data;
    if (keyCode == Keys.NUM_1) {
      shaderProgram.setFragmentShader(new WireframeFragmentShader());
    }
    if (keyCode == Keys.NUM_2) {
      shaderProgram.setFragmentShader(new LambertFragmentShader());
    }
    if (keyCode == Keys.NUM_3) {
      shaderProgram.setFragmentShader(new PhongLigtingFragmentShader());
    }
    if (keyCode == Keys.NUM_4) {
      shaderProgram.setFragmentShader(new PhongFragmentShader());
    }
    if (keyCode == Keys.NUM_5) {
      shaderProgram.setFragmentShader(new TexturedPhongFragmentShader());
    }
  }
}
