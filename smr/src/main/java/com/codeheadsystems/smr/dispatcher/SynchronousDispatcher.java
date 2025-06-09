package com.codeheadsystems.smr.dispatcher;

import com.codeheadsystems.smr.Callback;
import com.codeheadsystems.smr.Phase;
import com.codeheadsystems.smr.State;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The type Synchronous dispatcher.
 */
public class SynchronousDispatcher extends BaseDispatcher {

  /**
   * Instantiates a new Synchronous dispatcher.
   *
   * @param states the states
   */
  public SynchronousDispatcher(final Set<State> states) {
    super(states);
    log.info("SynchronousDispatcher()");
  }

  // A synchronized execution of the callbacks. Basic.
  @Override
  protected void executeCallbacks(final Set<Consumer<Callback>> phasedCallbacks,
                                  final Callback callback) {
    phasedCallbacks.forEach(consumer -> {
      executeCallback(consumer, callback);
    });
  }

}
