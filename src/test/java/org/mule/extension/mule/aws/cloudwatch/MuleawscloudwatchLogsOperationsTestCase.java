/*
 * Copyright (c) 2020.  The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),  a copy of which has been included with this distribution in the LICENSE.md file.
 */

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
    boolean payloadValue = (boolean) flowRunner( "putLogEventsFlow").run()
            .getMessage()
            .getPayload()
            .getValue();
    assertThat(payloadValue, is(true));
  }

  @Test
  public void executefilterLogEventsOperation() throws Exception  {
    Object payloadValue = (Object) flowRunner("filterLogEventsFlow").run()
            .getMessage()
            .getPayload()
            .getValue();
    assertThat(payloadValue, IsNull.notNullValue());

  }

  @Test
  public void executeputMetricDataOperation() throws Exception  {
    boolean payloadValue = (boolean)flowRunner("putMetricDataFlow").run()
            .getMessage()
            .getPayload()
            .getValue();
    assertThat(payloadValue, is(true));

  }
}
