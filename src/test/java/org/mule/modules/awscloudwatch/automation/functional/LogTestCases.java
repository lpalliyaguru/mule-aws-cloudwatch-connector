package org.mule.modules.awscloudwatch.automation.functional;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.awscloudwatch.AWSCloudwatchConnector;
import org.mule.tools.devkit.ctf.junit.AbstractTestCase;

public class LogTestCases extends AbstractTestCase<AWSCloudwatchConnector> {

	public LogTestCases() {
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
		java.lang.String message = null;
		java.lang.String type = null;
		assertEquals(getConnector().log(message, type), expected);
	}

}