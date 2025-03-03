package com.codeheadsystems.smr;

/**
 * The interface Decorator.
 *
 * @param <T> the type parameter
 */
public interface Decorator<T> {
  /**
   * Decorate t.
   *
   * @param t the t
   * @return the t
   */
  T decorate(T t);
}
