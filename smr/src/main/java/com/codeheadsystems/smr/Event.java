package com.codeheadsystems.smr;

import org.immutables.value.Value;
import org.immutables.value.Value.Immutable;

/**
 * An event causes the state machine (context) to be transitions from one state to another.
 */
@Immutable
public interface Event {

  /**
   * Of event.
   *
   * @param name the name
   * @return the event
   */
  static Event of(String name) {
    return ImmutableEvent.of(name);
  }

  /**
   * Name string.
   *
   * @return the string
   */
  @Value.Parameter
  String name();

}
