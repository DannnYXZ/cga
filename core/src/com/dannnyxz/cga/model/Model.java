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
public class Model {

  public List<Vec3> vertices = new ArrayList<>();
  public List<Polygon> polygons = new ArrayList<>();
  public List<Vec3> normals = new ArrayList<>();
}
