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

/**
 * The type Mocked data test.
 */
class MockedDataTest extends BaseJacksonTest<MockedData> {

  @Override
  protected Class<MockedData> getBaseClass() {
    return MockedData.class;
  }

  @Override
  protected MockedData getInstance() {
    return ImmutableMockedData.builder()
        .marshalledData("stuff")
        .build();
  }


}