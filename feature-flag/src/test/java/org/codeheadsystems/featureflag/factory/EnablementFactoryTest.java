package org.codeheadsystems.featureflag.factory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Enablement factory test.
 */
@ExtendWith(MockitoExtension.class)
class EnablementFactoryTest {

  private static final String TEST = "test";

  @Mock private Function<String, Integer> hashFunction;

  @InjectMocks private EnablementFactory enablementFactory;

  private static Stream<Arguments> provideGenerate() {
    return Stream.of(
        // normal cases
        Arguments.of(0.4, 0, true),
        Arguments.of(0.4, 20, true),
        Arguments.of(0.4, 40, true),
        Arguments.of(0.4, 50, false),
        Arguments.of(0.4, 99, false),
        Arguments.of(0.4, 100, true),

        // disabled cases
        Arguments.of(0.0, 0, false), // only true for generated
        Arguments.of(0.0, 20, false),
        Arguments.of(0.0, 40, false),
        Arguments.of(0.0, 50, false),
        Arguments.of(0.0, 99, false),
        Arguments.of(0.0, 100, false),
        Arguments.of(-0.1, 99, false),
        Arguments.of(-0.1, 0, false),
        Arguments.of(-0.1, 30, false),

        // enabled cases
        Arguments.of(1.0, 0, true),
        Arguments.of(1.0, 20, true),
        Arguments.of(1.0, 40, true),
        Arguments.of(1.0, 50, true),
        Arguments.of(1.0, 99, true),
        Arguments.of(1.0, 100, true),
        Arguments.of(2.0, 0, true),
        Arguments.of(2.0, 20, true),
        Arguments.of(2.0, 99, true)
    );
  }

  /**
   * Generate.
   *
   * @param featurePercentage the feature percentage
   * @param hashCodeInt       the hash code int
   * @param expected          the expected
   */
  @ParameterizedTest
  @MethodSource("provideGenerate")
  void generate(final double featurePercentage, final int hashCodeInt, final boolean expected) {
    lenient().when(hashFunction.apply(TEST)).thenReturn(hashCodeInt);
    assertThat(enablementFactory.generate(featurePercentage).enabled(TEST))
        .as("featurePercentage: %s, hashCodeInt: %s", featurePercentage, hashCodeInt)
        .isEqualTo(expected);
  }

  /**
   * Enabled feature.
   */
  @Test
  void enabledFeature() {
    assertThat(enablementFactory.enabledFeature().enabled(TEST)).isTrue();
  }

  /**
   * Disabled feature.
   */
  @Test
  void disabledFeature() {
    assertThat(enablementFactory.disabledFeature().enabled(TEST)).isFalse();
  }

  /**
   * Percentage feature.
   *
   * @param featurePercentage the feature percentage
   * @param hashCodeInt       the hash code int
   * @param expected          the expected
   */
  @ParameterizedTest
  @MethodSource("provideGenerate")
  void percentageFeature(final double featurePercentage, final int hashCodeInt, final boolean expected) {
    lenient().when(hashFunction.apply(TEST)).thenReturn(hashCodeInt);
    final boolean updatedExpected = (featurePercentage == 0.0 && hashCodeInt % 100 == 0 || expected); // 0.0 will come back as true
    assertThat(enablementFactory.percentageFeature(featurePercentage).enabled(TEST))
        .as("featurePercentage: %s, hashCodeInt: %s", featurePercentage, hashCodeInt)
        .isEqualTo(updatedExpected);
  }

}