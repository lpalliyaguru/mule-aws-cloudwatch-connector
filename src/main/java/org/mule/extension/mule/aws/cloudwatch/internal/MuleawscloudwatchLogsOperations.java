/*
 * Copyright (c) 2020.  The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),  a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.extension.mule.aws.cloudwatch.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import com.amazonaws.services.cloudwatch.model.*;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.*;
import org.mule.extension.mule.aws.cloudwatch.internal.resolver.OutputEntityResolver;
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
 * The entry class for all operations
 */
public class MuleawscloudwatchLogsOperations {

  private final Logger LOGGER = LoggerFactory.getLogger(MuleawscloudwatchLogsOperations.class);

  private volatile ArrayList<InputLogEvent> logEvents = new ArrayList();

  /**
   * This operation performs the PutLogEvent in the AWS Cloudwatch logs. Please see documentation at https://docs.aws.amazon.com/AmazonCloudWatchLogs/latest/APIReference/API_PutLogEvents.html
   * @param configuration The configuration instance
   * @param connection The configuration instance
   * @param logGroupName AWS log group name. This allows only one group name.
   * @param logStreamName AWS log stream name. This allows only one steam name.
   * @param logMessage AWS log message. Make sure you format the log message before providing to the connector. The connector will not format the message. Connector uses InputLogEvent in https://docs.aws.amazon.com/AmazonCloudWatchLogs/latest/APIReference/API_InputLogEvent.html
   * @return
   */
  @DisplayName("Put log events")
  @MediaType(value = ANY, strict = false)
  public boolean putLogEvents(
          @Config MuleawscloudwatchConfiguration configuration,
          @Connection MuleawscloudwatchConnection connection,
          String logGroupName,
          String logStreamName,
          String logMessage) {

    InputLogEvent logEvent = new InputLogEvent()
            .withMessage(logMessage)
            .withTimestamp(System.currentTimeMillis());
    this.logEvents.add(logEvent);
    PutLogEventsRequest logEventsRequest = new PutLogEventsRequest(logGroupName, logStreamName, this.logEvents)
            .withSequenceToken(this.getUploadSequenceToken(connection.getAwsCloudWatchLogClient(), logGroupName, logStreamName));
    this.logEvents.removeAll(logEventsRequest.getLogEvents());
    PutLogEventsResult result = connection.getAwsCloudWatchLogClient().putLogEvents(logEventsRequest);
    return true;
  }

  /**
   * This operation performs FilterLogEvents in AWS Cloudwatch. https://docs.aws.amazon.com/AmazonCloudWatchLogs/latest/APIReference/API_FilterLogEvents.html
   * @param configuration The configuration instance
   * @param connection The configuration instance
   * @param logGroupName AWS log group name. This allows only one group name.
   * @param logStreamName AWS log stream name. This allows only one steam name.
   * @param filterPattern The filter pattern. See the supported patterns here : https://docs.aws.amazon.com/AmazonCloudWatch/latest/logs/FilterAndPatternSyntax.html
   * @param startTime Start time. Optional. Example: 2020-01-01T00:00:00.000Z. To avoid confusions, please use UTC always.
   * @param endTime End time. Optional. Example: 2020-01-01T00:00:00.000Z. To avoid confusions, please use UTC always.
   * @param limit The limit of the returned result set. The maximum number of events to return. The default is 10,000 events.
   * @param nextToken Next token. If the response. The previous response will have the next token.
   * @return List
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
   * This operation performs PutMetricData in AWS Cloudwatch. https://docs.aws.amazon.com/AmazonCloudWatch/latest/APIReference/API_PutMetricData.html
   * @param configuration The configuration instance
   * @param connection The configuration instance
   * @param metricNameSpace Namespace of the metric. Example: Web/Traffic
   * @param dimensionName Name of the dimension. Connector allows only one dimension at a time
   * @param dimensionValue Value of the dimension. Connector allows only one dimension at a time
   * @param metricDatums List of metric datums. Example: [{metricName: 'Name of the metric', unitValue: 2.10}]
   * @return boolean
   */
  @DisplayName("Put metric data")
  @OutputResolver(output = OutputEntityResolver.class)
  public boolean putMetricData(
          @Config MuleawscloudwatchConfiguration configuration,
          @Connection MuleawscloudwatchConnection connection,
          String metricNameSpace,
          String dimensionName,
          String dimensionValue,
          List metricDatums
  ) {

    Dimension dimension  = new Dimension()
            .withName(dimensionName)
            .withValue(dimensionValue);
    List<MetricDatum> datumList = convertToMetricDatum(metricDatums, dimension);
    PutMetricDataRequest request = new PutMetricDataRequest()
            .withNamespace(metricNameSpace)
            .withMetricData(datumList);
    PutMetricDataResult result = connection.getAwsCloudWatch().putMetricData(request);
    return true;
  }

  /**
   * This method is to convert the user provided list of datums to SDK supported datums
   * @param datums The list of datums provided in the connector.
   * @param dimension dimension provided in the connector
   * @return
   */
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

  /**
   * Gets the intial sequence token for the logs creation
   * @param client The AWS Logs client
   * @param logGroupName the AWS log group name
   * @param logStreamName the AWS log stream name
   * @return String
   */
  private String getUploadSequenceToken(AWSLogsClient client, String logGroupName, String logStreamName) {
    DescribeLogStreamsRequest req = new DescribeLogStreamsRequest()
            .withLogGroupName(logGroupName)
            .withLogStreamNamePrefix(logStreamName)
            ;
    DescribeLogStreamsResult result = client.describeLogStreams(req);
    String seqToken = result.getLogStreams().get(0).getUploadSequenceToken();
    return seqToken;
  }
}
