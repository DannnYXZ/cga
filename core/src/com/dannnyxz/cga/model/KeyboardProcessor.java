package com.dannnyxz.cga.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyboardProcessor implements EventPublisher {

  private Map<Class<?>, List<EventListener>> subscribers = new HashMap<>();

  private static final List<Integer> processedKeys = Arrays.asList(
      Input.Keys.Q,
      Input.Keys.E,
      Input.Keys.U,
      Input.Keys.O,
      Input.Keys.A,
      Input.Keys.W,
      Input.Keys.S,
      Input.Keys.D,
      Input.Keys.I,
      Input.Keys.J,
      Input.Keys.K,
      Input.Keys.L,
      Input.Keys.LEFT,
      Input.Keys.RIGHT,
      Input.Keys.UP,
      Input.Keys.DOWN
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
