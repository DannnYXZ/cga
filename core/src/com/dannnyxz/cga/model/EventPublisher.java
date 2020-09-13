package com.dannnyxz.cga.model;

public interface EventPublisher {

  void subscribe(Event event, EventListener listener);

  void notify(Event event, Object data);
}
