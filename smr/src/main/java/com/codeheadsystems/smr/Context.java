package com.codeheadsystems.smr;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * You can have many contexts for a single state machine. And the
 * state machine manages this one context.
 */
public interface Context {

  /**
   * State state.
   *
   * @return the state
   */
  State state();

  /**
   * Sets the current state to the new state, returning the existing state, if any.
   *
   * @param newState for the context.
   * @return the old state
   */
  Optional<State> setState(State newState);
  /**
   * You can extend this to generate your own context easily enough.
   */
  abstract class Impl implements Context {

    /**
     * The State.
     */
    private final AtomicReference<State> reference;

    /**
     * Instantiates a new .
     *
     * @param initialState the initial state
     */
    public Impl(State initialState) {
      this.reference = new AtomicReference<>(initialState);
    }

    @Override
    public State state() {
      return reference.get();
    }

    @Override
    public Optional<State> setState(State newState) {
      return Optional.ofNullable(reference.getAndSet(newState));
    }

  }

}
