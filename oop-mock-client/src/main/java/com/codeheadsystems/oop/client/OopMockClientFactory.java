/*
 *    Copyright (c) 2022 Ned Wolpert <ned.wolpert@gmail.com>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.codeheadsystems.oop.client;

import com.codeheadsystems.oop.client.dagger.OopMockClientAssistedFactory;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Oop mock client factory.
 */
@Singleton
public class OopMockClientFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(OopMockClientFactory.class);

  private final LoadingCache<Class<?>, OopMockClient> cache;

  /**
   * Instantiates a new Oop mock client factory.
   *
   * @param assistedFactory the assisted factory
   */
  @Inject
  public OopMockClientFactory(final OopMockClientAssistedFactory assistedFactory) {
    LOGGER.info("OopMockClientFactory()");
    this.cache = Caffeine.newBuilder().build(assistedFactory::create);
  }

  /**
   * Generate oop mock client.
   *
   * @param clazz the clazz
   * @return the oop mock client
   */
  public OopMockClient generate(final Class<?> clazz) {
    LOGGER.info("generate({})", clazz);
    return cache.get(clazz);
  }

}
