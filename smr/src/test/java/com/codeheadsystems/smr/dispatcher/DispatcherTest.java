package com.codeheadsystems.smr.dispatcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codeheadsystems.smr.Callback;
import com.codeheadsystems.smr.Context;
import com.codeheadsystems.smr.Dispatcher;
import com.codeheadsystems.smr.Phase;
import com.codeheadsystems.smr.State;
import com.codeheadsystems.smr.TestBase;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Synchronous dispatcher test.
 */
@ExtendWith(MockitoExtension.class)
class DispatcherTest extends TestBase {

  @Mock private Consumer<Callback> consumer;
  @Mock private Consumer<Callback> secondConsumer;
  private Context context;
  @Captor private ArgumentCaptor<Callback> callback;

  @BeforeEach
  void setUp() {
    context = new Context.Impl(ONE) {
    };
  }

  static Stream<Arguments> dispatchers() {
    return Stream.of(
        Arguments.of(new SynchronousDispatcher(stateMachineDefinition.states())),
        Arguments.of(AsynchronousDispatcher.builder().withStates(stateMachineDefinition.states()).build())
    );
  }

  /**
   * Dispatch callbacks no exception.
   */
  @ParameterizedTest
  @MethodSource("dispatchers")
  void dispatchCallbacks_noException(Dispatcher dispatcher) {
    dispatcher.enable(ONE, Phase.TICK, consumer);
    dispatcher.dispatchCallbacks(context, ONE, Phase.TICK);
    verify(consumer).accept(callback.capture());
    assertThat(callback.getValue().context()).isEqualTo(context);
    assertThat(callback.getValue().state()).isEqualTo(ONE);
    assertThat(callback.getValue().phase()).isEqualTo(Phase.TICK);
  }

  /**
   * Dispatch callbacks no exception.
   */
  @ParameterizedTest
  @MethodSource("dispatchers")
  void dispatchCallbacks_disable(Dispatcher dispatcher) {
    dispatcher.enable(ONE, Phase.TICK, consumer);
    dispatcher.disable(ONE, Phase.TICK, consumer);
    dispatcher.dispatchCallbacks(context, ONE, Phase.TICK);
    verify(consumer, never()).accept(callback.capture());
  }

  @ParameterizedTest
  @MethodSource("dispatchers")
  void dispatchCallbacks_multipleConsumers(Dispatcher dispatcher) {
    dispatcher.enable(ONE, Phase.TICK, consumer);
    dispatcher.enable(ONE, Phase.TICK, secondConsumer);
    dispatcher.dispatchCallbacks(context, ONE, Phase.TICK);
    verify(consumer).accept(callback.capture());
    verify(secondConsumer).accept(callback.capture());
  }

  @ParameterizedTest
  @MethodSource("dispatchers")
  void dispatchCallbacks_multipleConsumersOneDisabled(Dispatcher dispatcher) {
    dispatcher.enable(ONE, Phase.TICK, consumer);
    dispatcher.enable(ONE, Phase.TICK, secondConsumer);
    dispatcher.disable(ONE, Phase.TICK, consumer);
    dispatcher.dispatchCallbacks(context, ONE, Phase.TICK);
    verify(consumer, never()).accept(callback.capture());
    verify(secondConsumer).accept(callback.capture());
  }

  /**
   * Dispatch callbacks with exception.
   */
  @ParameterizedTest
  @MethodSource("dispatchers")
  void dispatchCallbacks_withException(Dispatcher dispatcher) {
    doThrow(new RuntimeException("test")).when(consumer).accept(callback.capture());
    dispatcher.enable(ONE, Phase.TICK, consumer);
    dispatcher.dispatchCallbacks(context, ONE, Phase.TICK);
    assertThat(callback.getValue().context()).isEqualTo(context);
    assertThat(callback.getValue().state()).isEqualTo(ONE);
    assertThat(callback.getValue().phase()).isEqualTo(Phase.TICK);
  }

  /**
   * Handle transition event.
   */
  @ParameterizedTest
  @MethodSource("dispatchers")
  void handleTransitionEvent(Dispatcher dispatcher) {
    dispatcher.enable(ONE, Phase.EXIT, consumer);
    dispatcher.enable(TWO, Phase.ENTER, consumer);
    context.setState(ONE);
    dispatcher.handleTransitionEvent(context, ONE, TWO);
    verify(consumer, times(2)).accept(callback.capture());
    assertThat(callback.getAllValues().get(0).context()).isEqualTo(context);
    assertThat(callback.getAllValues().get(0).state()).isEqualTo(ONE);
    assertThat(callback.getAllValues().get(0).phase()).isEqualTo(Phase.EXIT);
    assertThat(callback.getAllValues().get(1).context()).isEqualTo(context);
    assertThat(callback.getAllValues().get(1).state()).isEqualTo(TWO);
    assertThat(callback.getAllValues().get(1).phase()).isEqualTo(Phase.ENTER);
  }

  /**
   * Handle transition event no fail when context has wronge state.
   */
  @ParameterizedTest
  @MethodSource("dispatchers")
  void handleTransitionEvent_noFailWhenContextHasWrongeState(Dispatcher dispatcher) {
    dispatcher.enable(ONE, Phase.EXIT, consumer);
    dispatcher.enable(TWO, Phase.ENTER, consumer);
    context.setState(THREE);
    dispatcher.handleTransitionEvent(context, ONE, TWO);
    verify(consumer, times(2)).accept(callback.capture());
    assertThat(callback.getAllValues().get(0).context()).isEqualTo(context);
    assertThat(callback.getAllValues().get(0).state()).isEqualTo(ONE);
    assertThat(callback.getAllValues().get(0).phase()).isEqualTo(Phase.EXIT);
    assertThat(callback.getAllValues().get(1).context()).isEqualTo(context);
    assertThat(callback.getAllValues().get(1).state()).isEqualTo(TWO);
    assertThat(callback.getAllValues().get(1).phase()).isEqualTo(Phase.ENTER);
  }

}