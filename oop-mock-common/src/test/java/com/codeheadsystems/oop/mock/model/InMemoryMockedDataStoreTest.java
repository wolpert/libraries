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

package com.codeheadsystems.oop.mock.model;

import com.codeheadsystems.test.model.BaseJacksonTest;
import java.util.Map;

/**
 * The type In memory mocked data store test.
 */
class InMemoryMockedDataStoreTest extends BaseJacksonTest<InMemoryMockedDataStore> {

  @Override
  protected Class<InMemoryMockedDataStore> getBaseClass() {
    return InMemoryMockedDataStore.class;
  }

  @Override
  protected InMemoryMockedDataStore getInstance() {
    return ImmutableInMemoryMockedDataStore.builder()
        .putDatastore("key",
            Map.of("otherkey",
                ImmutableMockedData.builder().marshalledData("marshalled").build()))
        .build();
  }
}