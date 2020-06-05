/*
 * Copyright (c) 2020.  The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),  a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.extension.mule.aws.cloudwatch.internal;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.AWSLogsClientBuilder;

/**
 * This class provides the AWS Cloudwatch and AWS CloudwatchLogs connections
 */
public final class MuleawscloudwatchConnection {

  public AmazonCloudWatch awsCloudWatch;
  public AWSLogsClient awsCloudWatchLogClient;

  public MuleawscloudwatchConnection(String key, String secret, String region) {
    this.initCloudwatch(key, secret, region);
  }

  public AWSLogsClient getAwsCloudWatchLogClient() {
    return awsCloudWatchLogClient;
  }

  /**
   * Method to initialize the connections
   * @param key AWS Key
   * @param secret AWS Secret
   * @param region AWS region
   */
  private void initCloudwatch(String key, String secret, String region) {
    BasicAWSCredentials credentials       = new BasicAWSCredentials(key, secret);
    AWSStaticCredentialsProvider provider = new AWSStaticCredentialsProvider(credentials);
    this.awsCloudWatch =  AmazonCloudWatchClientBuilder
            .standard()
            .withCredentials(provider)
            .withRegion(region)
            .build();
    this.awsCloudWatchLogClient = (AWSLogsClient) AWSLogsClientBuilder
            .standard()
            .withCredentials(provider)
            .withRegion(region)
            .build();
  }

  public AmazonCloudWatch getAwsCloudWatch() {
    return awsCloudWatch;
  }

  public void invalidate() {
    this.awsCloudWatch.shutdown();
  }
}
