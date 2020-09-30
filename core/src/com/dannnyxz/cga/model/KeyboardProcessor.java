package com.dannnyxz.cga.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardProcessor implements EventPublisher {

  private Map<Class<?>, List<EventListener>> subscribers = new HashMap<>();

  private static final List<Integer> processedKeys = Arrays.asList(
      Keys.Q,
      Keys.O,
      Keys.E,
      Keys.U,
      Keys.NUM_1,
      Keys.NUM_2,
      Keys.NUM_3,
      Keys.NUM_4,
      Keys.NUM_5,
      Keys.NUM_6,
      Keys.A,
      Keys.W,
      Keys.S,
      Keys.D,
      Keys.I,
      Keys.J,
      Keys.K,
      Keys.L,
      Keys.LEFT,
      Keys.RIGHT,
      Keys.UP,
      Keys.DOWN,
      Keys.SHIFT_LEFT,
      Keys.SPACE
  );

  @Override
  public void subscribe(Event event, EventListener listener) {
    List<EventListener> listeners = subscribers.get(event.getClass());
    if (listeners == null) {
      subscribers.put(event.getClass(), new ArrayList<EventListener>() {{
        add(listener);
      }});
    } else {
      listeners.add(listener);
    }
  }

  @Override
  public void notify(Event event, Object data) {
    List<EventListener> targets = subscribers.get(event.getClass());
    if (targets != null) {
      targets.forEach(x -> x.update(data));
    }
  }

  public void processInput() {
    for (Integer keyCode : processedKeys) {
      if (Gdx.input.isKeyPressed(keyCode)) {
        notify(new KeyboardEvent(), keyCode);
      }
    }
  }
}
