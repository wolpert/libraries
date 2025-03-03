package com.codeheadsystems.smr;

import java.util.concurrent.atomic.AtomicReference;

/**
 * You can have many contexts for a single state machine. And the
 * state machine manages this one context.
 */
@FunctionalInterface
public interface Context {

  /**
   * Reference atomic reference.
   *
   * @return the atomic reference
   */
  AtomicReference<State> reference();

  /**
   * State state.
   *
   * @return the state
   */
  default State state() {
    return reference().get();
  }

  /**
   * You can extend this to generate your own context easily enough.
   */
  abstract class Impl implements Context {

    /**
     * The State.
     */
    protected final AtomicReference<State> state;

    /**
     * Instantiates a new .
     *
     * @param initialState the initial state
     */
    public Impl(State initialState) {
      this.state = new AtomicReference<>(initialState);
    }

    @Override
    public AtomicReference<State> reference() {
      return state;
    }

  }

}
