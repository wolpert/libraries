package com.codeheadsystems.smr;

/**
 * Provides for an unchecked exception class useful for the state machine.
 */
public class StateMachineException extends RuntimeException {
  /**
   * Instantiates a new State machine exception.
   *
   * @param message the message
   */
  public StateMachineException(String message) {
    super(message);
  }

  /**
   * Instantiates a new State machine exception.
   *
   * @param message the message
   * @param e       the e
   */
  public StateMachineException(final String message, final Exception e) {
    super(message, e);
  }
}
