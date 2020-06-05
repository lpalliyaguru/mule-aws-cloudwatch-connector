/*
 * Copyright (c) 2020.  The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),  a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.extension.mule.aws.cloudwatch.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class (as it's name implies) provides connection instances and the funcionality to disconnect and validate those
 * connections.
 * <p>
 * All connection related parameters (values required in order to create a connection) must be
 * declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares that connections resolved by this provider
 * will be pooled and reused. There are other implementations like {@link CachedConnectionProvider} which lazily creates and
 * caches connections or simply {@link ConnectionProvider} if you want a new connection each time something requires one.
 */
public class MuleawscloudwatchConnectionProvider implements PoolingConnectionProvider<MuleawscloudwatchConnection> {

  private final Logger LOGGER = LoggerFactory.getLogger(MuleawscloudwatchConnectionProvider.class);

  /**
   * Config for AWS Access key
   */
  @Parameter
  @DisplayName("AWS access key")
  private String awsAccessKey;

  /**
   * Config for AWS Secret key
   */
  @Parameter
  @DisplayName("AWS secret key")
  private String awsSecretKey;

  /**
  * Config for AWS Region
  */
  @Parameter
  @DisplayName("AWS region")
  @Optional(defaultValue = "us-east-1")
  private String awsRegion;

  @Override
  public MuleawscloudwatchConnection connect() throws ConnectionException {
    return new MuleawscloudwatchConnection(awsAccessKey, awsSecretKey, awsRegion);
  }

  @Override
  public void disconnect(MuleawscloudwatchConnection connection) {
    try {
        connection.invalidate();
    } catch (Exception e) {
        LOGGER.error("Error while disconnecting " + e.getMessage(), e);
    }
  }

  @Override
  public ConnectionValidationResult validate(MuleawscloudwatchConnection connection) {
      return ConnectionValidationResult.success();
  }

  public String getAwsAccessKey() {
      return awsAccessKey;
  }

  public void setAwsAccessKey(String awsAccessKey) {
      this.awsAccessKey = awsAccessKey;
  }

  public String getAwsSecretKey() {
      return awsSecretKey;
  }

  public void setAwsSecretKey(String awsSecretKey) {
      this.awsSecretKey = awsSecretKey;
  }

  public String getAwsRegion() {
      return awsRegion;
  }

  public void setAwsRegion(String awsRegion) {
      this.awsRegion = awsRegion;
  }
}
