package com.codeheadsystems.smr;

import static org.slf4j.LoggerFactory.getLogger;

import com.codeheadsystems.smr.dispatcher.SynchronousDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.slf4j.Logger;

/**
 * State machines manage the current state(context) and transitions between states.
 * It uses a state machine definition to determine the valid transitions. The dispatcher
 * is used to handle the callbacks for transition listeners.
 */
public class StateMachine extends Context.Impl {

  private static final Logger log = getLogger(StateMachine.class);

  private final StateMachineDefinition definition;
  private final Dispatcher dispatcher;
  private final boolean useExceptions;

  /**
   * Instantiates a new State machine.
   *
   * @param definition    the definition
   * @param dispatcher    the dispatcher
   * @param useExceptions the use exceptions
   */
  StateMachine(final StateMachineDefinition definition,
               final Dispatcher dispatcher,
               final boolean useExceptions) {
    super(definition.initialState());
    log.info("StateMachineImpl():{}", definition.initialState());
    this.definition = definition;
    this.dispatcher = dispatcher;
    this.useExceptions = useExceptions;
  }

  /**
   * Builder state machine . builder.
   *
   * @return the state machine . builder
   */
  public static StateMachine.Builder builder() {
    return new StateMachine.Builder();
  }

  /**
   * The current state of the state machine.
   *
   * @return the current state.
   */
  public State state() {
    return state.get();
  }

  /**
   * Get the states that are valid for the current state machine.
   *
   * @return set of states.
   */
  public Set<State> states() {
    return definition.states();
  }

  /**
   * Get the events that are valid for the current state.
   *
   * @return set of events
   */
  public Set<Event> events() {
    return events(state());
  }

  /**
   * Get the events that are valid for the given state.
   *
   * @param state to check.
   * @return set of event.
   */
  public Set<Event> events(final State state) {
    return definition.events(state);
  }

  /**
   * Enable.
   *
   * @param state           the state
   * @param phase           the phase
   * @param contextConsumer the context consumer
   */
  public void enable(final State state,
                     final Phase phase,
                     final Consumer<Callback> contextConsumer) {
    if (definition.hasState(state)) {
      dispatcher.enable(state, phase, contextConsumer);
    } else {
      returnOrThrow(false, () -> new StateMachineException("State " + state + " is not in the state machine."));
    }
  }

  /**
   * Disable.
   *
   * @param state           the state
   * @param phase           the phase
   * @param contextConsumer the context consumer
   */
  public void disable(final State state,
                      final Phase phase,
                      final Consumer<Callback> contextConsumer) {
    if (definition.hasState(state)) {
      dispatcher.disable(state, phase, contextConsumer);
    } else {
      returnOrThrow(false, () -> new StateMachineException("State " + state + " is not in the state machine."));
    }
  }

  /**
   * Dispatch an event to the state machine.
   *
   * @param event to dispatch.
   * @return the new state if changed. Else the current state.
   */
  public State dispatch(final Event event) {
    final State currentState = state();
    log.trace("dispatch({},{})", event, currentState);
    final Optional<State> optionalNewState = definition.forEvent(currentState, event);
    if (optionalNewState.isPresent()) {
      final State newState = optionalNewState.get();
      dispatcher.handleTransitionEvent(this, currentState, newState, event);
      return newState;
    } else {
      log.warn("No transition for event {} from state {}", event, currentState);
      return returnOrThrow(currentState,
          () -> new StateMachineException("No transition for event " + event + " from state " + currentState));
    }
  }

  /**
   * Tick the state machine. Basically causes the callbacks on the current
   * state to execute.
   */
  public void tick() {
    dispatcher.dispatchCallbacks(this, state(), Phase.TICK);
  }

  private <T> T returnOrThrow(final T t,
                              final Supplier<StateMachineException> supplier) {
    if (useExceptions) {
      throw supplier.get();
    }
    return t;
  }

  /**
   * The type Builder.
   */
  public static class Builder {

    private final List<Decorator<Dispatcher>> dispatcherDecorators;
    private StateMachineDefinition stateMachineDefinition;
    private Dispatcher dispatcher;
    private boolean useExceptions;

    /**
     * Instantiates a new Builder.
     */
    public Builder() {
      this.useExceptions = false;
      this.dispatcherDecorators = new ArrayList<>();
    }

    /**
     * With state machine definition builder.
     *
     * @param stateMachineDefinition the state machine definition
     * @return the builder
     */
    public Builder withStateMachineDefinition(final StateMachineDefinition stateMachineDefinition) {
      this.stateMachineDefinition = stateMachineDefinition;
      return this;
    }

    /**
     * With dispatcher decorator builder.
     *
     * @param decorator the decorator
     * @return the builder
     */
    public Builder withDispatcherDecorator(final Decorator<Dispatcher> decorator) {
      dispatcherDecorators.add(decorator);
      return this;
    }

    /**
     * With dispatcher builder.
     *
     * @param dispatcher the dispatcher
     * @return the builder
     */
    public Builder withDispatcher(final Dispatcher dispatcher) {
      this.dispatcher = dispatcher;
      return this;
    }

    /**
     * With use exceptions builder.
     *
     * @param useExceptions the use exceptions
     * @return the builder
     */
    public Builder withUseExceptions(final boolean useExceptions) {
      this.useExceptions = useExceptions;
      return this;
    }

    /**
     * Build state machine.
     *
     * @return the state machine
     */
    public StateMachine build() {
      if (stateMachineDefinition == null) {
        throw new StateMachineException("StateMachineDefinition is required.");
      }
      if (dispatcher == null) {
        dispatcher = new SynchronousDispatcher(stateMachineDefinition.states());
      }
      Dispatcher decoratoedDispatcher = dispatcher;
      for (Decorator<Dispatcher> decorator : dispatcherDecorators) {
        decoratoedDispatcher = decorator.decorate(decoratoedDispatcher);
      }
      return new StateMachine(stateMachineDefinition, decoratoedDispatcher, useExceptions);
    }

  }

}
