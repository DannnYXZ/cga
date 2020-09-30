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
public class Polygon {
  public Vec3 norm;
  public List<Vec3> norms = new ArrayList<>();
  public List<Vec3> vertices = new ArrayList<>();
  public List<Vec3> tex = new ArrayList<>();
}
