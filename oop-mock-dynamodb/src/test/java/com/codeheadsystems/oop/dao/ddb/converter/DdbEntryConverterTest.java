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

package com.codeheadsystems.oop.dao.ddb.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.codeheadsystems.oop.dao.ddb.model.DdbEntry;
import com.codeheadsystems.oop.mock.Hasher;
import com.codeheadsystems.oop.mock.converter.JsonConverter;
import com.codeheadsystems.oop.mock.model.MockedData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Ddb entry converter test.
 */
@ExtendWith(MockitoExtension.class)
class DdbEntryConverterTest {

  /**
   * The constant NAMESPACE.
   */
  public static final String NAMESPACE = "namespace";
  /**
   * The constant LOOKUP.
   */
  public static final String LOOKUP = "lookup";
  /**
   * The constant DISCRIMINATOR.
   */
  public static final String DISCRIMINATOR = "discriminator";
  /**
   * The constant HASH.
   */
  public static final String HASH = "hash";
  /**
   * The constant RANGE.
   */
  public static final String RANGE = "range";
  /**
   * The constant ENTRY_WITHOUT_DATA.
   */
  public static final DdbEntry ENTRY_WITHOUT_DATA = new DdbEntry(HASH, RANGE);
  private static final String JSON = "JSON";
  /**
   * The constant ENTRY_WITH_DATA.
   */
  public static final DdbEntry ENTRY_WITH_DATA = new DdbEntry(HASH, RANGE, JSON);

  @Mock private Hasher hasher;
  @Mock private JsonConverter jsonConverter;
  @Mock private MockedData mockedData;

  private DdbEntryConverter converter;

  /**
   * Sets .
   */
  @BeforeEach
  void setup() {
    converter = new DdbEntryConverter(hasher, jsonConverter);
  }

  /**
   * Convert without mock data.
   */
  @Test
  void convert_withoutMockData() {
    when(hasher.hash(LOOKUP, DISCRIMINATOR)).thenReturn(RANGE);

    assertThat(converter.convert(NAMESPACE, LOOKUP, DISCRIMINATOR))
        .isNotNull()
        .hasFieldOrPropertyWithValue("hash", NAMESPACE)
        .hasFieldOrPropertyWithValue("range", RANGE)
        .hasFieldOrPropertyWithValue("mockData", null);
  }

  /**
   * Convert with mock data.
   */
  @Test
  void convert_withMockData() {
    when(hasher.hash(LOOKUP, DISCRIMINATOR)).thenReturn(RANGE);
    when(jsonConverter.toJson(mockedData)).thenReturn(JSON);

    assertThat(converter.convert(NAMESPACE, LOOKUP, DISCRIMINATOR, mockedData))
        .isNotNull()
        .hasFieldOrPropertyWithValue("hash", NAMESPACE)
        .hasFieldOrPropertyWithValue("range", RANGE)
        .hasFieldOrPropertyWithValue("mockData", JSON);
  }

  /**
   * To mocked data nofield.
   */
  @Test
  void toMockedData_nofield() {
    assertThat(converter.toMockedData(ENTRY_WITHOUT_DATA))
        .isEmpty();
  }

  /**
   * To mocked data withfield.
   */
  @Test
  void toMockedData_withfield() {
    when(jsonConverter.convert(JSON, MockedData.class)).thenReturn(mockedData);

    assertThat(converter.toMockedData(ENTRY_WITH_DATA))
        .isNotEmpty()
        .contains(mockedData);
  }
}