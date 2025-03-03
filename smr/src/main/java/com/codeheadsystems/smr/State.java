package com.codeheadsystems.smr;

import org.immutables.value.Value;

/**
 * A state of the state machine (context).
 */
@Value.Immutable
public interface State {

  /**
   * Of state.
   *
   * @param name the name
   * @return the state
   */
  static State of(String name) {
    return ImmutableState.of(name);
  }

  /**
   * Name string.
   *
   * @return the string
   */
  @Value.Parameter
  String name();

}
