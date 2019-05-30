package org.mule.modules.awscloudwatch.automation.functional;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.awscloudwatch.AWSCloudwatchConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class PutLogEventTestCases extends AbstractTestCase<AWSCloudwatchConnector> {

	public PutLogEventTestCases() {
		super(AWSCloudwatchConnector.class);
	}

	@Before
	public void setup() {
		// TODO
	}

	@After
	public void tearDown() {
		// TODO
	}

	@Test
	public void verify() {
		java.lang.String expected = null;
		java.lang.String logType = null;
		java.lang.String message = null;
		assertEquals(getConnector().putLogEvent(logType, message), expected);
	}

}