package org.mule.extension.mule.aws.cloudwatch.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import com.amazonaws.services.cloudwatch.model.*;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.*;
import org.mule.extension.mule.aws.cloudwatch.internal.resolver.LogTypeKeyResolver;
import org.mule.extension.mule.aws.cloudwatch.internal.resolver.OutputEntityResolver;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.OutputResolver;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class MuleawscloudwatchLogsOperations {

  private final Logger LOGGER = LoggerFactory.getLogger(MuleawscloudwatchLogsOperations.class);

  private volatile ArrayList<InputLogEvent> logEvents = new ArrayList();
  /**
   * Example of an operation that uses the configuration and a connection instance to perform some action.
   */
  @DisplayName("Put log events")
  @MediaType(value = ANY, strict = false)
  public String putLogEvents(
          @Config MuleawscloudwatchConfiguration configuration,
          @Connection MuleawscloudwatchConnection connection,
          String logGroupName,
          String logStreamName,
          String logMessage,
          @MetadataKeyId(LogTypeKeyResolver.class) String logType) {
    InputLogEvent logEvent = new InputLogEvent()
            .withMessage(this.getLoggableMessage(logType.toString(), logMessage))
            .withTimestamp(System.currentTimeMillis());
    this.logEvents.add(logEvent);
    PutLogEventsRequest logEventsRequest = new PutLogEventsRequest(logGroupName, logStreamName, this.logEvents)
            .withSequenceToken(this.getUploadSequenceToken(connection.getAwsCloudWatchLogClient(), logGroupName, logStreamName));
    this.logEvents.removeAll(logEventsRequest.getLogEvents());
    PutLogEventsResult result = connection.getAwsCloudWatchLogClient().putLogEvents(logEventsRequest);
    return "LOG: "  + logMessage;
  }

  /**
   *
   * @param configuration
   * @param connection
   * @param logGroupName
   * @param logStreamName
   * @param filterPattern
   * @param startTime
   * @param endTime
   * @return
   */
  @DisplayName("Filter log events")
  @OutputResolver(output = OutputEntityResolver.class)
  public Object[] FilterLogEvents(@Config MuleawscloudwatchConfiguration configuration,
                                  @Connection MuleawscloudwatchConnection connection,
                                  String logGroupName,
                                  String logStreamName,
                                  String filterPattern,
                                  @Optional Date startTime,
                                  @Optional Date endTime,
                                  @Optional int limit,
                                  @Optional String nextToken) {
    FilterLogEventsRequest request = new FilterLogEventsRequest()
            .withLogGroupName(logGroupName)
            .withLogStreamNames(logStreamName)
            .withFilterPattern(filterPattern)
            ;

    if (startTime != null) {
      request.withStartTime(startTime.getTime());
    }

    if (endTime != null) {
      request.withEndTime(endTime.getTime());
    }

    if (limit != 0) {
      request.withLimit(limit);
    }

    if (nextToken != null) {
      request.withNextToken(nextToken);
    }

    FilterLogEventsResult result = connection.getAwsCloudWatchLogClient().filterLogEvents(request);

    return result.getEvents().toArray();
  }

  /**
   * AWS API Documentation: https://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_PutMetricData.html
   * @param configuration
   * @param connection
   * @return
   */
  @DisplayName("Put metric data")
  @OutputResolver(output = OutputEntityResolver.class)
  public boolean putMetricData(
          @Config MuleawscloudwatchConfiguration configuration,
          @Connection MuleawscloudwatchConnection connection,
          String metricNameSpace,
          String dimentionName,
          String dimentionValue,
          List metricDatums
  ) {

    Dimension dimension  = new Dimension()
            .withName(dimentionName)
            .withValue(dimentionValue);
    List<MetricDatum> datumList = convertToMetricDatum(metricDatums, dimension);
    PutMetricDataRequest request = new PutMetricDataRequest()
            .withNamespace(metricNameSpace)
            .withMetricData(datumList);
    PutMetricDataResult result = connection.getAwsCloudWatch().putMetricData(request);
    return true;
  }

  private List<MetricDatum> convertToMetricDatum (List<Map> datums, Dimension dimension) {
    List<MetricDatum> list = new ArrayList<>();
    for (Map datum : datums) {
      list.add(new MetricDatum()
              .withMetricName((String)datum.get("metricName"))
              .withValue((Double) datum.get("unitValue"))
              .withDimensions(dimension)
      );
    }
    return list;

  }

  private String getUploadSequenceToken(AWSLogsClient client, String logGroupName, String logStreamName) {
    DescribeLogStreamsRequest req = new DescribeLogStreamsRequest()
            .withLogGroupName(logGroupName)
            .withLogStreamNamePrefix(logStreamName)
            ;
    DescribeLogStreamsResult result = client.describeLogStreams(req);
    String seqToken = result.getLogStreams().get(0).getUploadSequenceToken();
    return seqToken;
  }

  private String getLoggableMessage(String type, String message) {
    return String.format(
            "%s %s",
            type,
            message
    );
  }
}
