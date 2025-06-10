package com.codeheadsystems.smr.dispatcher;

import com.codeheadsystems.smr.Callback;
import com.codeheadsystems.smr.State;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type asynchronous dispatcher.
 */
public class AsynchronousDispatcher extends BaseDispatcher {

  protected static final Logger log = LoggerFactory.getLogger(AsynchronousDispatcher.class);
  private final Executor executor;

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Instantiates a new Synchronous dispatcher.
   *
   * @param states   the states
   * @param executor
   */
  private AsynchronousDispatcher(final Set<State> states, final Executor executor) {
    super(states);
    this.executor = executor;
    log.info("SynchronousDispatcher()");
  }

  // A synchronized execution of the callbacks. Basic.
  @Override
  protected void executeCallbacks(final Set<Consumer<Callback>> phasedCallbacks,
                                  final Callback callback) {
    phasedCallbacks.stream()
        .map(callbackConsumer ->
            CompletableFuture.runAsync(() -> executeCallback(callbackConsumer, callback), executor))
        .forEach(CompletableFuture::join);
  }

  public static class Builder {

    private Executor executor;
    private Set<State> states;

    public Builder withExecutor(Executor executor) {
      this.executor = executor;
      return this;
    }

    public Builder withStates(Set<State> states) {
      this.states = states;
      return this;
    }

    public AsynchronousDispatcher build() {
      if (states == null) {
        throw new IllegalArgumentException("states must not be null");
      }
      Executor localExecutor = executor;
      if (localExecutor == null) {
        localExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
      }
      return new AsynchronousDispatcher(states, localExecutor);
    }

  }

}
