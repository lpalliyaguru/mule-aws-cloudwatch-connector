package org.mule.extension.mule.aws.cloudwatch.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class MuleawscloudwatchLogsOperations {

  /**
   * Example of an operation that uses the configuration and a connection instance to perform some action.
   */
  @DisplayName("putLogEvents")
  @MediaType(value = ANY, strict = false)
  public String putLogEvents(@Config MuleawscloudwatchConfiguration configuration, @Connection MuleawscloudwatchConnection connection, String logMessage){
    return "LOG: "  + logMessage;
  }

}
