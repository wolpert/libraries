package com.codeheadsystems.metrics.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codeheadsystems.metrics.Tags;
import com.codeheadsystems.metrics.TagsGenerator;
import com.codeheadsystems.metrics.helper.TagsGeneratorRegistry;
import java.time.Clock;
import java.time.Duration;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Metrics impl test.
 */
@ExtendWith(MockitoExtension.class)
class MetricsImplTest {

  private static final String METRIC_NAME = "name";
  private static final Tags DEFAULT_TAGS = new Tags("a", "1", "b", "2");
  private static final Tags OVERRIDE_TAGS = new Tags("b", "3", "c", "4");
  private static final String[] OVERRIDE_ARRAY = new String[]{"b", "3", "c", "4"};
  private static final Tags COMBINED_TAGS = new Tags("a", "1", "b", "3", "c", "4");
  private static final Object RESULT = new Object();
  private static final Tags ERROR_TAGS = new Tags("error", "true");
  private static final Tags RESULT_TAGS = new Tags("result", "true");
  private static final TagsGenerator<Object> TAGS_GENERATOR_RESULT = object -> RESULT_TAGS;
  private static final TagsGenerator<Throwable> TAGS_GENERATOR_ERROR = object -> ERROR_TAGS;
  private static final Function<String, String> metricsName = Function.identity();

  @Mock private MetricPublisher metricPublisher;
  @Mock private Clock clock;
  @Mock private TagsGeneratorRegistry tagsGeneratorRegistry;

  private MetricsImpl metricsImpl;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    metricsImpl = new MetricsImpl(
        clock,
        metricPublisher,
        null,
        null,
        DEFAULT_TAGS, metricsName);
  }

  /**
   * Test and.
   */
  @Test
  void testAnd() {
    metricsImpl.and(OVERRIDE_TAGS);
    assertThat(metricsImpl.getTags())
        .isEqualTo(COMBINED_TAGS);
  }

  /**
   * Close.
   *
   * @throws Exception the exception
   */
  @Test
  void close() throws Exception {
    metricsImpl.and(OVERRIDE_TAGS);
    metricsImpl.close();
    assertThat(metricsImpl.getTags())
        .isEqualTo(DEFAULT_TAGS);
    verify(metricPublisher).close();
  }

  /**
   * Add array.
   */
  @Test
  void addArray() {
    metricsImpl.and(OVERRIDE_ARRAY);
    assertThat(metricsImpl.getTags())
        .isEqualTo(COMBINED_TAGS);
  }

  /**
   * Test increment.
   */
  @Test
  void testIncrement() {
    metricsImpl.increment(METRIC_NAME, 1L, OVERRIDE_ARRAY);
    verify(metricPublisher)
        .increment(METRIC_NAME, 1L, COMBINED_TAGS);
    assertThat(metricsImpl.getTags())
        .isEqualTo(DEFAULT_TAGS);
  }

  /**
   * Test publish time.
   */
  @Test
  void testPublishTime() {
    metricsImpl.publishTime(METRIC_NAME, Duration.ofMillis(100), COMBINED_TAGS);
    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), COMBINED_TAGS);
  }

  /**
   * Time base.
   */
  @Test
  void time_base() {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, this::testMethod);

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS);
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time base hardcoded method.
   */
  @Test
  void time_base_hardcodedMethod() {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, () -> RESULT);

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS);
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time manual tags.
   */
  @Test
  void time_manualTags() {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, this::testMethod, "something", "else");

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS.from("something", "else"));
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time more manual tags.
   */
  @Test
  void time_moreManualTags() {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, this::testMethod, "something", "else", "another", "tag");

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS.from("something", "else", "another", "tag"));
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time base exception.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseException() throws SomeException {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionDefined);

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS);
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time base with exception.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseWithException() throws SomeException {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    assertThatExceptionOfType(SomeException.class)
        .isThrownBy(() -> metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionThrown));

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS);
  }

  /**
   * Time base with exception default handler.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseWithException_defaultHandler() throws SomeException {
    metricsImpl = new MetricsImpl(clock, metricPublisher, TAGS_GENERATOR_ERROR, null, DEFAULT_TAGS, metricsName);
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    assertThatExceptionOfType(SomeException.class)
        .isThrownBy(() -> metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionThrown));

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS.from(ERROR_TAGS));
  }

  /**
   * Time with tags.
   */
  @Test
  void time_withTags() {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, this::testMethod, OVERRIDE_ARRAY);

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), COMBINED_TAGS);
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time with tags changed in method.
   */
  @Test
  void time_withTags_changedInMethod() {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Tags tags = Tags.of(OVERRIDE_ARRAY);
    final Object result = metricsImpl.time(METRIC_NAME, () -> testMethodWithTags(tags, "added", "true"), tags);

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), Tags.of(COMBINED_TAGS).add("added", "true"));
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time base exception with tags.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseException_withTags() throws SomeException {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionDefined, OVERRIDE_ARRAY);

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), COMBINED_TAGS);
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time base with exception with tags.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseWithException_withTags() throws SomeException {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    assertThatExceptionOfType(SomeException.class)
        .isThrownBy(() -> metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionThrown, OVERRIDE_ARRAY));

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), COMBINED_TAGS);
  }

  /**
   * Time base exception with tag generator.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseException_withTagGenerator() throws SomeException {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionDefined, TAGS_GENERATOR_RESULT, TAGS_GENERATOR_ERROR);

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS.from(RESULT_TAGS));
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time base with exception with generator.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseWithException_withGenerator() throws SomeException {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    assertThatExceptionOfType(SomeException.class)
        .isThrownBy(() -> metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionThrown, TAGS_GENERATOR_RESULT, TAGS_GENERATOR_ERROR));

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), DEFAULT_TAGS.from(ERROR_TAGS));
  }

  /**
   * Time base exception with tag generator and override tags.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseException_withTagGenerator_andOverrideTags() throws SomeException {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    final Object result = metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionDefined, TAGS_GENERATOR_RESULT, TAGS_GENERATOR_ERROR, OVERRIDE_ARRAY);

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), COMBINED_TAGS.from(RESULT_TAGS));
    assertThat(result).isEqualTo(RESULT);
  }

  /**
   * Time base with exception with generator and override tags.
   *
   * @throws SomeException the some exception
   */
  @Test
  void time_baseWithException_withGenerator_andOverrideTags() throws SomeException {
    when(clock.millis()).thenReturn(200L).thenReturn(300L);
    assertThatExceptionOfType(SomeException.class)
        .isThrownBy(() -> metricsImpl.time(METRIC_NAME, this::testMethodWithExceptionThrown, TAGS_GENERATOR_RESULT, TAGS_GENERATOR_ERROR, OVERRIDE_ARRAY));

    verify(metricPublisher)
        .time(METRIC_NAME, Duration.ofMillis(100), COMBINED_TAGS.from(ERROR_TAGS));
  }

  /**
   * Test method object.
   *
   * @return the object
   */
  Object testMethod() {
    return RESULT;
  }

  /**
   * Test method with tags object.
   *
   * @param tags    the tags
   * @param newTags the new tags
   * @return the object
   */
  Object testMethodWithTags(Tags tags, String... newTags) {
    tags.add(newTags);
    return RESULT;
  }

  /**
   * Test method with exception thrown object.
   *
   * @return the object
   * @throws SomeException the some exception
   */
  Object testMethodWithExceptionThrown() throws SomeException {
    throw new SomeException();
  }

  /**
   * Test method with exception defined object.
   *
   * @return the object
   * @throws SomeException the some exception
   */
  Object testMethodWithExceptionDefined() throws SomeException {
    return RESULT;
  }


  /**
   * The type Some exception.
   */
  static class SomeException extends Exception {
    /**
     * Instantiates a new Some exception.
     */
    public SomeException() {
      super("oops");
    }
  }

}