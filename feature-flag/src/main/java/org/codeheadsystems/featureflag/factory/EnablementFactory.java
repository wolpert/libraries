package org.codeheadsystems.featureflag.factory;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;
import org.apache.commons.codec.digest.MurmurHash3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Enablement factory.
 */
public class EnablementFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(EnablementFactory.class);
  private final Function<String, Integer> hashFunction;

  /**
   * Instantiates a new Enablement factory.
   */
  public EnablementFactory() {
    this(EnablementFactory::defaultHash);
  }

  /**
   * Instantiates a new Enablement factory.
   *
   * @param hashFunction the hash function
   */
  public EnablementFactory(Function<String, Integer> hashFunction) {
    this.hashFunction = hashFunction;
    LOGGER.info("EnablementFactory({})", hashFunction);
  }

  private static int defaultHash(String discriminator) {
    byte[] bytes = discriminator.getBytes(StandardCharsets.UTF_8);
    return MurmurHash3.hash32x86(bytes);
  }

  /**
   * Generate feature.
   *
   * @param percentage the percentage
   * @return the feature
   */
  public Enablement generate(double percentage) {
    LOGGER.trace("generate({})", percentage);
    if (percentage <= 0.0) {
      LOGGER.trace("Disabled feature");
      return disabledFeature();
    } else if (percentage >= 1.0) {
      LOGGER.trace("Enabled feature");
      return enabledFeature();
    } else {
      LOGGER.trace("Calculated feature");
      return percentageFeature(percentage);
    }
  }

  /**
   * Enabled feature feature.
   *
   * @return the feature
   */
  public Enablement enabledFeature() {
    LOGGER.info("enabledFeature()");
    return (discriminator) -> true;
  }

  /**
   * Disabled feature feature.
   *
   * @return the feature
   */
  public Enablement disabledFeature() {
    LOGGER.info("disabledFeature()");
    return (discriminator) -> false;
  }

  /**
   * Percentage feature feature.
   *
   * @param percentage the percentage
   * @return the feature
   */
  public Enablement percentageFeature(double percentage) {
    LOGGER.info("percentageFeature({})", percentage);
    return (discriminator) -> {
      final int hash = hashFunction.apply(discriminator);
      final double calculated = ((double) (hash % 100)) / 100.0;
      LOGGER.trace("percentageFeature({}:{}) -> {} {}", discriminator, percentage, calculated, calculated < percentage);
      return calculated <= percentage;
    };
  }

}
