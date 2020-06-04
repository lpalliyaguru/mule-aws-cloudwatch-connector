package org.mule.extension.mule.aws.cloudwatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import org.hamcrest.core.IsNot;
import org.hamcrest.core.IsNull;
import org.mule.functional.junit4.MuleArtifactFunctionalTestCase;
import org.junit.Test;

import java.util.ArrayList;

public class MuleawscloudwatchLogsOperationsTestCase extends MuleArtifactFunctionalTestCase {

  /**
   * Specifies the mule config xml with the flows that are going to be executed in the tests, this file lives in the test resources.
   */
  @Override
  protected String getConfigFile() {
    return "test-mule-config.xml";
  }

  @Test
  public void executeputLogEventsOperation() throws Exception  {
    String payloadValue = ((String) flowRunner("putLogEventsFlow").run()
            .getMessage()
            .getPayload()
            .getValue());
    assertThat(payloadValue, is("LOG: Loggable Message"));
  }

  @Test
  public void executefilterLogEventsOperation() throws Exception  {
    Object payloadValue = (Object) flowRunner("filterLogEventsFlow").run()
            .getMessage()
            .getPayload()
            .getValue();
    assertThat(payloadValue, IsNull.notNullValue());

  }
}
