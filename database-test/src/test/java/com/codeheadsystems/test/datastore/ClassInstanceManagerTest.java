package com.codeheadsystems.test.datastore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

public class ClassInstanceManagerTest {

  private ClassInstanceManager classInstanceManager;

  @BeforeEach
  public void setUp() {
    classInstanceManager = new ClassInstanceManager();
  }

  @Test
  public void testSetInstance() {
    String instance = "testInstance";
    classInstanceManager.setInstance(String.class, instance);
    assertThat(classInstanceManager.getInstance(String.class))
        .isPresent()
        .contains(instance);
  }

  @Test
  public void testGetInstance() {
    String instance = "testInstance";
    classInstanceManager.setInstance(String.class, instance);
    Optional<String> retrievedInstance = classInstanceManager.getInstance(String.class);
    assertThat(retrievedInstance)
        .isPresent().
        contains(instance);
  }

  @Test
  public void testRemoveInstance() {
    String instance = "testInstance";
    classInstanceManager.setInstance(String.class, instance);
    Optional<String> removedInstance = classInstanceManager.removeInstance(String.class);
    assertThat(removedInstance)
        .isPresent()
        .contains(instance);
    assertThat(classInstanceManager.getInstance(String.class)).isNotPresent();
  }

  @Test
  public void testClear() {
    classInstanceManager.setInstance(String.class, "testInstance");
    classInstanceManager.setInstance(Integer.class, 123);
    classInstanceManager.clear();
    assertThat(classInstanceManager.getInstance(String.class)).isNotPresent();
    assertThat(classInstanceManager.getInstance(Integer.class)).isNotPresent();
  }

  @Test
  public void testHasInstance() {
    classInstanceManager.setInstance(String.class, "testInstance");
    assertThat(classInstanceManager.hasInstance(String.class)).isTrue();
    assertThat(classInstanceManager.hasInstance(Integer.class)).isFalse();
  }
}