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

package com.codeheadsystems.oop.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.codeheadsystems.oop.mock.converter.JsonConverter;
import com.codeheadsystems.oop.mock.model.MockedData;
import com.codeheadsystems.oop.mock.translator.JsonTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Json translator test.
 */
@ExtendWith(MockitoExtension.class)
class JsonTranslatorTest {

  private static final String MARSHALLED = "marshalled";
  private static final String REAL_DATA = "real data";

  @Mock private JsonConverter converter;
  @Mock private MockedData mockedData;

  private JsonTranslator translator;

  /**
   * Sets up.
   */
  @BeforeEach
  void setUp() {
    translator = new JsonTranslator(converter);
  }

  /**
   * Unmarshal.
   */
  @Test
  void unmarshal() {
    when(mockedData.marshalledData()).thenReturn(MARSHALLED);
    when(converter.convert(MARSHALLED, String.class)).thenReturn(REAL_DATA);

    final String result = translator.unmarshal(String.class, mockedData);

    assertThat(result)
        .isEqualTo(REAL_DATA);
  }

  /**
   * Marshal.
   */
  @Test
  void marshal() {
    when(converter.toJson(REAL_DATA))
        .thenReturn(MARSHALLED);

    final MockedData result = translator.marshal(REAL_DATA);

    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("marshalledData", MARSHALLED);

  }

}