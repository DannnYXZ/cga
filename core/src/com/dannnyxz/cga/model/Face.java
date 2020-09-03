package com.dannnyxz.cga.model;

import com.dannnyxz.cga.math.Vec3;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class Face {
  public Vec3 norm;
  public List<Integer> vertices = new ArrayList<>();
  public Vec3 tex;
}
