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
  public void testPut() {
    String instance = "testInstance";
    classInstanceManager.put(String.class, instance);
    assertThat(classInstanceManager.get(String.class))
        .isPresent()
        .contains(instance);
  }

  @Test
  public void testPut_withoutClass() {
    String instance = "testInstance";
    classInstanceManager.put(instance);
    assertThat(classInstanceManager.get(String.class))
        .isPresent()
        .contains(instance);
  }


  @Test
  public void testGet() {
    String instance = "testInstance";
    classInstanceManager.put(String.class, instance);
    Optional<String> retrievedInstance = classInstanceManager.get(String.class);
    assertThat(retrievedInstance)
        .isPresent().
        contains(instance);
  }

  @Test
  public void testRemoveInstance() {
    String instance = "testInstance";
    classInstanceManager.put(String.class, instance);
    Optional<String> removedInstance = classInstanceManager.remove(String.class);
    assertThat(removedInstance)
        .isPresent()
        .contains(instance);
    assertThat(classInstanceManager.get(String.class)).isNotPresent();
  }

  @Test
  public void testClear() {
    classInstanceManager.put(String.class, "testInstance");
    classInstanceManager.put(Integer.class, 123);
    classInstanceManager.clear();
    assertThat(classInstanceManager.get(String.class)).isNotPresent();
    assertThat(classInstanceManager.get(Integer.class)).isNotPresent();
  }

  @Test
  public void testHasInstance() {
    classInstanceManager.put(String.class, "testInstance");
    assertThat(classInstanceManager.hasInstance(String.class)).isTrue();
    assertThat(classInstanceManager.hasInstance(Integer.class)).isFalse();
  }
}