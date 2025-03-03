package com.codeheadsystems.smr;


/**
 * The type Test base.
 */
public class TestBase {

  /**
   * The constant ONE.
   */
  public static final State ONE = State.of("one");
  /**
   * The constant TWO.
   */
  public static final State TWO = State.of("two");
  /**
   * The constant THREE.
   */
  public static final State THREE = State.of("three");
  /**
   * The constant FOUR.
   */
  public static final State FOUR = State.of("four");
  /**
   * The constant TO_TWO.
   */
  public static final Event TO_TWO = Event.of("ToTwo");
  /**
   * The constant TO_THREE.
   */
  public static final Event TO_THREE = Event.of("ToThree");
  /**
   * The constant TO_ONE.
   */
  public static final Event TO_ONE = Event.of("ToOne");

  /**
   * The State machine definition.
   */
  protected StateMachineDefinition stateMachineDefinition = StateMachineDefinition.builder()
      .addState(ONE).addState(TWO).addState(THREE)
      .setInitialState(ONE)
      .addTransition(ONE, TO_TWO, TWO)
      .addTransition(TWO, TO_THREE, THREE)
      .addTransition(THREE, TO_TWO, TWO)
      .addTransition(TWO, TO_ONE, ONE)
      .build();


  /**
   * Sets up state machine.
   *
   * @param withException the with exception
   * @param decorators    the decorators
   * @return the up state machine
   */
  public StateMachine setUpStateMachine(boolean withException, Decorator<Dispatcher>... decorators) {
    StateMachine.Builder builder = StateMachine.builder();
    for (Decorator<Dispatcher> decorator : decorators) {
      builder.withDispatcherDecorator(decorator);
    }
    return builder.withUseExceptions(withException)
        .withStateMachineDefinition(stateMachineDefinition)
        .build();
  }

}
