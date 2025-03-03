package com.codeheadsystems.smr.yml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.codeheadsystems.smr.StateMachineDefinition;
import org.junit.jupiter.api.Test;

/**
 * The type Definition test.
 */
class DefinitionTest extends TestBase {

  /**
   * Round trip.
   */
  @Test
  void roundTrip() {
    Definition definition = Definition.disassemble(stateMachineDefinition);
    StateMachineDefinition reassembled = definition.assemble();
    assertThat(reassembled).isEqualTo(stateMachineDefinition);
  }

  /**
   * Missing initial state.
   */
  @Test
  void missingInitialState() {
    Definition definition = Definition.disassemble(stateMachineDefinition);
    definition.setInitialState("missing");
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(definition::assemble);
  }

}