package org.mule.extension.mule.aws.cloudwatch.internal;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;

/**
 * This class represents an extension connection just as example (there is no real connection with anything here c:).
 */
public final class MuleawscloudwatchConnection {

  public AmazonCloudWatch amazonCloudWatch;
  public String id;

  public MuleawscloudwatchConnection(String key, String secret, String region) {
    this.id = "aws-connection";
    this.amazonCloudWatch = obtainAmazonCloudwatchConnection(key, secret, region);
  }

  private AmazonCloudWatch obtainAmazonCloudwatchConnection(String key, String secret, String region) {
    BasicAWSCredentials creds = new BasicAWSCredentials(key, secret);
    return AmazonCloudWatchClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(creds))
            .withRegion(region)
            .build();
  }

  public String getId() {
    return id;
  }

  public AmazonCloudWatch getAmazonCloudWatch() {
    return amazonCloudWatch;
  }

  public void invalidate() {
    this.amazonCloudWatch.shutdown();
    // do something to invalidate this connection!
  }
}
