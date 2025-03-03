package com.codeheadsystems.smr.yml;

import com.codeheadsystems.smr.StateMachineDefinition;
import com.codeheadsystems.smr.StateMachineException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

/**
 * The type Yaml manager.
 */
public class YamlManager {

  private final ObjectMapper objectMapper;

  /**
   * Instantiates a new Yaml manager.
   */
  public YamlManager() {
    this(new ObjectMapper(new YAMLFactory()));
  }

  /**
   * Instantiates a new Yaml manager.
   *
   * @param objectMapper the object mapper
   */
  public YamlManager(final ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
    this.objectMapper.findAndRegisterModules();
  }

  /**
   * To yaml string.
   *
   * @param definition the definition
   * @return the string
   */
  public String toYaml(final StateMachineDefinition definition) {
    try {
      return objectMapper.writeValueAsString(Definition.disassemble(definition));
    } catch (Exception e) {
      throw new StateMachineException("Failed to convert to yaml.", e);
    }
  }

  /**
   * From yaml state machine definition.
   *
   * @param yaml the yaml
   * @return the state machine definition
   */
  public StateMachineDefinition fromYaml(final String yaml) {
    try {
      final Definition definition = objectMapper.readValue(yaml, Definition.class);
      return definition.assemble();
    } catch (Exception e) {
      throw new StateMachineException("Failed to convert from yaml.", e);
    }
  }

}
