/**
 * (c) 2018 INSEAD. The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.awscloudwatch.automation.functional;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.glassfish.hk2.api.Self;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.awscloudwatch.AWSCloudwatchConnector;
import org.mule.modules.awscloudwatch.config.ConnectorConfig;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class PutLogEventTestCases extends AbstractTestCase<AWSCloudwatchConnector> {

	public PutLogEventTestCases() {
		super(AWSCloudwatchConnector.class);
	}

	@Before
	public void setup() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void verify() {
		java.lang.String logType = "ERROR";
		java.lang.String message = "Test Message";
		assertNotNull(getConnector().putLogEvent(logType, message));
	}
}