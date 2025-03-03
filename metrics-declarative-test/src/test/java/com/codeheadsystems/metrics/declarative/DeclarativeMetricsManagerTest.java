package com.codeheadsystems.metrics.declarative;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import com.codeheadsystems.metrics.Tags;
import com.codeheadsystems.metrics.impl.MetricPublisher;
import com.codeheadsystems.metrics.impl.NullMetricsImpl;
import java.io.IOException;
import javax.swing.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Declarative metrics manager test.
 */
@ExtendWith(MockitoExtension.class)
class DeclarativeMetricsManagerTest {

  @Mock private MetricPublisher metricPublisher;

  private SampleObject sampleObject;

  /**
   * Setup sample object.
   */
  @BeforeEach
  void setupSampleObject(){
    sampleObject = new SampleObject();
    sampleObject.metricsFactory(metricPublisher);
  }

  /**
   * Verify metrics exists via instance.
   */
  @Test
  void verifyMetricsExistsViaInstance() {
    DeclarativeMetricsManager.metrics().increment("test", Tags.empty());
    verify(metricPublisher).increment("test", 1L, Tags.empty());
  }

  /**
   * Metrics factory is created.
   */
  @Test
  void metricsFactoryIsCreated() {
    assertThat(DeclarativeMetricsManager.metrics())
        .isNotNull()
        .isNotInstanceOf(NullMetricsImpl.class);
  }

  /**
   * Method without metrics.
   */
  @Test
  void methodWithoutMetrics() {
    sampleObject.methodWithoutMetrics();
    verifyNoInteractions(metricPublisher);
  }

  /**
   * Method with metrics.
   */
  @Test
  void methodWithMetrics() {
    sampleObject.methodWithMetrics();
    verify(metricPublisher).time(eq("SampleObject.methodWithMetrics"), any(), eq(Tags.empty()));
  }

  /**
   * Method with metrics and tags return true.
   */
  @Test
  void methodWithMetricsAndTagsReturnTrue() {
    assertThat(sampleObject.methodWithMetricsAndTagsReturnTrue("value")).isTrue();
    verify(metricPublisher).time(eq("metricsNameWasOverridden"), any(), eq(Tags.of("name", "value")));
  }

  /**
   * Method with metrics and tags with defined exception.
   *
   * @throws IOException the io exception
   */
  @Test
  void methodWithMetricsAndTagsWithDefinedException() throws IOException {
    assertThat(sampleObject.methodWithMetricsAndTagsWithDefinedException("value", "fred")).isTrue();
    verify(metricPublisher).time(eq("SampleObject.methodWithMetricsAndTagsWithDefinedException"), any(), eq(Tags.of("anotherName", "value")));
  }

  /**
   * Method with metrics and tags with thrown exception.
   */
  @Test
  void methodWithMetricsAndTagsWithThrownException() {
    assertThatExceptionOfType(IOException.class).isThrownBy(() -> sampleObject.methodWithMetricsAndTagsWithThrownException("value", "smith"));
    verify(metricPublisher).time(eq("SampleObject.methodWithMetricsAndTagsWithThrownException"), any(), eq(Tags.of("name", "value", "thing", "smith")));
  }

  /**
   * Method with metrics and tags and thrown runtime exception.
   */
  @Test
  void methodWithMetricsAndTagsAndThrownRuntimeException()  {
    assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> sampleObject.methodWithMetricsAndTagsAndThrownRuntimeException(null));
    verify(metricPublisher).time(eq("SampleObject.methodWithMetricsAndTagsAndThrownRuntimeException"), any(), eq(Tags.of("notname", "null")));
  }

}