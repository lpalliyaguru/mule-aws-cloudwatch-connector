package org.mule.extension.mule.aws.cloudwatch.internal;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.AWSLogsClientBuilder;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class MuleawscloudwatchConnection {

  public AmazonCloudWatch awsCloudWatch;
  public AWSLogsClient awsCloudWatchLogClient;
  public String id;

  public MuleawscloudwatchConnection(String key, String secret, String region) {
    this.id = "aws-connection";
    this.initCloudwatch(key, secret, region);
  }

  public AWSLogsClient getAwsCloudWatchLogClient() {
    return awsCloudWatchLogClient;
  }

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

  public String getId() {
    return id;
  }

  public AmazonCloudWatch getAwsCloudWatch() {
    return awsCloudWatch;
  }

  public void invalidate() {
    this.awsCloudWatch.shutdown();
    // do something to invalidate this connection!
  }
}
