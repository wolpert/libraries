package com.codeheadsystems.metrics.declarative;

import com.codeheadsystems.metrics.MetricFactory;
import com.codeheadsystems.metrics.Tags;
import com.codeheadsystems.metrics.impl.MetricPublisher;
import java.io.IOException;
import java.time.Duration;

/**
 * The type Sample object.
 */
public class SampleObject {

  /**
   * Metrics factory metric factory.
   *
   * @param metricPublisher the metric publisher
   * @return the metric factory
   */
  @DeclarativeFactory
  public MetricFactory metricsFactory(MetricPublisher metricPublisher) {
    return MetricFactory.builder()
        .withMetricPublisher(metricPublisher)
        .build();
  }


  /**
   * Method without metrics.
   */
  public void methodWithoutMetrics() {

  }

  /**
   * Method with metrics.
   */
  @Metrics
  public void methodWithMetrics() {
  }

  /**
   * Method with metrics and tags boolean.
   *
   * @param name the a name
   * @return the boolean
   */
  @Metrics("metricsNameWasOverridden")
  public Boolean methodWithMetricsAndTagsReturnTrue(@Tag("name") String name) {
    return true;
  }

  /**
   * Method with metrics and tags with defined exception boolean.
   *
   * @param name  the name
   * @param other the other
   * @return the boolean
   * @throws IOException the io exception
   */
  @Metrics
  public boolean methodWithMetricsAndTagsWithDefinedException(@Tag("anotherName") String name, String other) throws IOException {
    return true;
  }

  /**
   * Method with metrics and tags with thrown exception boolean.
   *
   * @param name  the name
   * @param other the other
   * @return the boolean
   * @throws IOException the io exception
   */
  @Metrics
  public boolean methodWithMetricsAndTagsWithThrownException(@Tag("name") String name, @Tag("thing") String other) throws IOException {
    throw new IOException();
  }

  /**
   * Method with metrics and tags and thrown runtime exception.
   *
   * @param name the name
   */
  @Metrics
  public void methodWithMetricsAndTagsAndThrownRuntimeException(@Tag("notname") String name) {
    throw new IllegalStateException();
  }
}
