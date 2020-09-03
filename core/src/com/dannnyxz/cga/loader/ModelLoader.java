package com.dannnyxz.cga.loader;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.dannnyxz.cga.math.Vec3;
import com.dannnyxz.cga.model.Face;
import com.dannnyxz.cga.model.Model;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModelLoader {

  public static Model load(String objModelPath) {
    FileHandle file = Gdx.files.internal(objModelPath);
    try (BufferedReader br = new BufferedReader(file.reader())) {
      Model model = new Model();
      br.lines().forEach(s -> {
        if (s.startsWith("v ")) {
          List<Float> coords = Arrays.stream(s.split("\\s"))
              .skip(1)
              .map(Float::parseFloat)
              .collect(Collectors.toList());
          model.vertices().add(new Vec3(coords.get(0), coords.get(1), coords.get(2)));
        } else if (s.startsWith("f ")) {
          List<List<String>> chunks = Arrays.stream(s.split("\\s"))
              .skip(1).map(x -> Arrays.asList(x.split("/")))
              .collect(Collectors.toList());

          Face face = new Face();
          if (chunks.get(0).size() >= 1) {
            face.vertices.add(parseInt(chunks.get(0).get(0)) - 1);
            face.vertices.add(parseInt(chunks.get(1).get(0)) - 1);
            face.vertices.add(parseInt(chunks.get(2).get(0)) - 1);
          }
          if (chunks.get(0).size() >= 2) {
            face.tex = new Vec3(
                parseFloat(chunks.get(0).get(1)),
                parseFloat(chunks.get(1).get(1)),
                parseFloat(chunks.get(2).get(1)));
          }
          model.faces.add(face);


        }
      });
      return model;
    } catch (IOException e) {
      throw new RuntimeException("Failed to parse model");
    }
  }
}
