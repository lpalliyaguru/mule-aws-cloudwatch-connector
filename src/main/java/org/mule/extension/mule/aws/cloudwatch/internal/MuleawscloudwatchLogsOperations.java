package org.mule.extension.mule.aws.cloudwatch.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.model.*;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

import java.util.ArrayList;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MuleawscloudwatchLogsOperations {

  private volatile ArrayList<InputLogEvent> logEvents = new ArrayList();
  /**
   * Example of an operation that uses the configuration and a connection instance to perform some action.
   */
  @DisplayName("putLogEvents")
  @MediaType(value = ANY, strict = false)
  public String putLogEvents(@Config MuleawscloudwatchConfiguration configuration, @Connection MuleawscloudwatchConnection connection, String logGroupName, String logStreamName, String logMessage){
    InputLogEvent logEvent = new InputLogEvent().withMessage(logMessage).withTimestamp(System.currentTimeMillis());
    this.logEvents.add(logEvent);
    PutLogEventsRequest logEventsRequest = new PutLogEventsRequest(logGroupName, logStreamName, this.logEvents)
            .withSequenceToken(this.getUploadSequenceToken(connection.getAwsCloudWatchLogClient(), logGroupName, logStreamName));
    this.logEvents.removeAll(logEventsRequest.getLogEvents());
    PutLogEventsResult result = connection.getAwsCloudWatchLogClient().putLogEvents(logEventsRequest);
    return "LOG: "  + logMessage;
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

}
