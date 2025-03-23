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

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/**
 * Setups the ddb instance.
 */
public class DynamoDbExtension implements BeforeAllCallback, AfterAllCallback, BeforeEachCallback, ParameterResolver {

  private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbExtension.class);
  private static final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(DynamoDbExtension.class);

  private String randomPort() {
    return String.valueOf((int) (Math.random() * 10000 + 1000));
  }

  private ClassInstanceManager classInstanceManager(final ExtensionContext context) {
    return context.getStore(namespace)
        .getOrComputeIfAbsent(ClassInstanceManager.class,
            k -> new ClassInstanceManager(),
            ClassInstanceManager.class);
  }

  @Override
  public void beforeAll(final ExtensionContext context) throws Exception {
    LOGGER.info("Setting in memory DynamoDB local instance");
    final ClassInstanceManager classInstanceManager = classInstanceManager(context);
    final String port = randomPort();
    final DynamoDBProxyServer server = ServerRunner.createServerFromCommandLineArgs(
        new String[]{"-inMemory", "-port", port});
    server.start();
    AmazonDynamoDB client = getAmazonDynamoDb(port);
    classInstanceManager.put(DynamoDBProxyServer.class, server);
    classInstanceManager.put(AmazonDynamoDB.class, client);
    classInstanceManager.put(DynamoDbClient.class, getDynamoDbClient(port));
    classInstanceManager.put(DynamoDBMapper.class, new DynamoDBMapper(client));
  }

  @Override
  public void afterAll(final ExtensionContext context) {
    LOGGER.info("Tearing down in memory DynamoDB local instance");
    final ClassInstanceManager classInstanceManager = context.getStore(namespace).remove(ClassInstanceManager.class, ClassInstanceManager.class);
    if (classInstanceManager == null) {
      LOGGER.error("No class instance manager found");
      return;
    }
    classInstanceManager.remove(DynamoDBProxyServer.class).ifPresent(o -> {
      try {
        o.stop();
      } catch (Exception e) {
        LOGGER.error("Error stopping DynamoDB proxy server", e);
      }
    });
    classInstanceManager.remove(DynamoDBMapper.class);
    classInstanceManager.remove(AmazonDynamoDB.class);
    classInstanceManager.remove(DynamoDbClient.class).ifPresent(DynamoDbClient::close);
    classInstanceManager.clear();
  }

  @Override
  public boolean supportsParameter(final ParameterContext parameterContext,
                                   final ExtensionContext extensionContext) throws ParameterResolutionException {
    final Class<?> type = parameterContext.getParameter().getType();
    final ClassInstanceManager classInstanceManager = classInstanceManager(extensionContext);
    return parameterContext.isAnnotated(DataStore.class) && classInstanceManager.hasInstance(type);
  }

  @Override
  public Object resolveParameter(final ParameterContext parameterContext,
                                 final ExtensionContext extensionContext) throws ParameterResolutionException {
    final Class<?> type = parameterContext.getParameter().getType();
    final ClassInstanceManager classInstanceManager = classInstanceManager(extensionContext);
    return classInstanceManager.get(type).orElseGet(() -> {
      LOGGER.error("No instance found for type {}", type.getSimpleName());
      return null;
    });
  }

  private AmazonDynamoDB getAmazonDynamoDb(String port) {
    final AWSCredentials credentials = new BasicAWSCredentials("one", "two");
    final AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(credentials);
    final AwsClientBuilder.EndpointConfiguration configuration =
        new AwsClientBuilder.EndpointConfiguration("http://localhost:" + port, "us-west-2");

    return AmazonDynamoDBClientBuilder.standard()
        .withCredentials(provider)
        .withEndpointConfiguration(configuration)
        .build();
  }

  private DynamoDbClient getDynamoDbClient(final String port) {

    final AwsCredentials credentials = AwsBasicCredentials.create("one", "two");
    final AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);

    try {
      return DynamoDbClient.builder()
          .credentialsProvider(credentialsProvider)
          .region(Region.US_EAST_1)
          .endpointOverride(new URI("http://localhost:" + port))
          .build();
    } catch (URISyntaxException e) {
      throw new IllegalStateException("Should not have happened given the hardcoded url", e);
    }
  }

  @Override
  public void beforeEach(final ExtensionContext context) {
    final ClassInstanceManager classInstanceManager = classInstanceManager(context);
    context.getRequiredTestInstances().getAllInstances().forEach(instance -> {
      Arrays.stream(instance.getClass().getDeclaredFields())
          .filter(f -> f.isAnnotationPresent(DataStore.class))
          .forEach(field -> {
            setValueForField(classInstanceManager, instance, field);
          });
    });
  }

  private void setValueForField(final ClassInstanceManager classInstanceManager,
                                final Object o,
                                final Field field) {
    final Object value = classInstanceManager.get(field.getType())
        .orElseThrow(() -> new IllegalArgumentException("Unable to find DynamoDB extension value of type " + field.getType())); // Check the store to see we have this type.
    LOGGER.info("Setting field {}:{}", field.getName(), field.getType().getSimpleName());
    enableSettingTheField(field);
    try {
      field.set(o, value);
    } catch (IllegalAccessException e) {
      LOGGER.error("Unable to set the field value for {}", field.getName(), e);
      LOGGER.error("Continuing, but expect nothing good will happen next.");
    }
  }

  /**
   * This allows us to set the field directly. It will fail if the security manager in play disallows it.
   * We can talk about justifications all we want, but really we know Java is not Smalltalk. Meta programming
   * is limited here. So... we try to do the right thing.
   *
   * @param field to change accessibility for.
   */
  protected void enableSettingTheField(final Field field) {
    try {
      field.setAccessible(true);
    } catch (RuntimeException re) {
      LOGGER.error("Unable to change accessibility for field due to private var or security manager: {}",
          field.getName());
      LOGGER.error("The setting will likely fail. Consider changing that type to protected.", re);
    }
  }
}
