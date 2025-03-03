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

package com.codeheadsystems.test.datastore;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * The type Dynamo db extension test.
 */
@ExtendWith(DynamoDbExtension.class)
class DynamoDbExtensionTest {

  /**
   * The constant ATTRIBUTE.
   */
  public static final String ATTRIBUTE = "aAttribute";
  /**
   * The constant RANGE.
   */
  public static final String RANGE = "aRange";
  /**
   * The constant HASH.
   */
  public static final String HASH = "aHash";
  @DataStore private DynamoDBMapper mapper;
  @DataStore private AmazonDynamoDB amazonDynamoDb;

  /**
   * Sets .
   */
  @BeforeEach
  void setup() {
    amazonDynamoDb.createTable(
        mapper.generateCreateTableRequest(Entry.class)
            .withBillingMode(BillingMode.PAY_PER_REQUEST));
  }

  /**
   * Tear down.
   */
  @AfterEach
  void tearDown() {
    // force the table empty
    amazonDynamoDb.deleteTable(mapper.generateDeleteTableRequest(Entry.class));
  }

  /**
   * Test client.
   */
  @Test
  void testClient() {
    assertThat(amazonDynamoDb)
        .isNotNull();
  }

  /**
   * Test mapper.
   */
  @Test
  void testMapper() {
    assertThat(mapper)
        .isNotNull();
  }

  /**
   * Table exists and is empty.
   */
  @Test
  void tableExistsAndIsEmpty() {
    final DynamoDBScanExpression expression = new DynamoDBScanExpression();
    final PaginatedScanList<Entry> result = mapper.scan(Entry.class, expression);

    assertThat(result)
        .isEmpty();
  }

  /**
   * We can add items.
   */
  @Test
  void weCanAddItems() {
    final Entry entry = new Entry(HASH, RANGE, ATTRIBUTE);
    mapper.save(entry);
    final Entry result = mapper.load(Entry.class, HASH, RANGE);
    assertThat(result)
        .isNotNull()
        .hasFieldOrPropertyWithValue("attribute", ATTRIBUTE);
  }

}