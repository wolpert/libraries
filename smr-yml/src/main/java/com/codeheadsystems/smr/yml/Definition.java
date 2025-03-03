package com.codeheadsystems.smr.yml;

import com.codeheadsystems.smr.Event;
import com.codeheadsystems.smr.State;
import com.codeheadsystems.smr.StateMachineDefinition;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Definition.
 */
public class Definition {

  private String initialState;
  private Map<String, Map<String, String>> transitions;

  /**
   * Disassemble definition.
   *
   * @param stateMachineDefinition the state machine definition
   * @return the definition
   */
  public static Definition disassemble(final StateMachineDefinition stateMachineDefinition) {
    Definition definition = new Definition();
    definition.setInitialState(stateMachineDefinition.initialState().name());
    definition.setTransitions(new HashMap<>());
    stateMachineDefinition.getTransitions().forEach((state, eventMap) -> {
      HashMap<String, String> eventMapString = new HashMap<>();
      eventMap.forEach((event, nextState) -> eventMapString.put(event.name(), nextState.name()));
      definition.getTransitions().put(state.name(), eventMapString);
    });
    return definition;
  }

  /**
   * Assemble state machine definition.
   *
   * @return the state machine definition
   */
  @JsonIgnore
  public StateMachineDefinition assemble() {
    StateMachineDefinition.Builder builder = StateMachineDefinition.builder();
    // add states
    transitions.forEach((state, eventMap) -> {
      builder.addState(state);
      eventMap.forEach((event, nextState) -> {
        builder.addState(nextState);
      });
    });
    // add transitions
    transitions.forEach((state, eventMap) -> {
      eventMap.forEach((event, nextState) -> {
        builder.addTransition(State.of(state), Event.of(event), State.of(nextState));
      });
    });
    return builder.setInitialState(State.of(initialState)).build();
  }

  /**
   * Gets initial state.
   *
   * @return the initial state
   */
  public String getInitialState() {
    return initialState;
  }

  /**
   * Sets initial state.
   *
   * @param initialState the initial state
   */
  public void setInitialState(final String initialState) {
    this.initialState = initialState;
  }

  /**
   * Gets transitions.
   *
   * @return the transitions
   */
  public Map<String, Map<String, String>> getTransitions() {
    return transitions;
  }

  /**
   * Sets transitions.
   *
   * @param transitions the transitions
   */
  public void setTransitions(final Map<String, Map<String, String>> transitions) {
    this.transitions = transitions;
  }
}
