/**
 * (c) 2018 INSEAD. The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.awscloudwatch.automation.runner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mule.modules.awscloudwatch.AWSCloudwatchConnector;
import org.mule.modules.awscloudwatch.automation.functional.PutLogEventTestCases;
import org.mule.tools.devkit.ctf.mockup.ConnectorTestContext;

@RunWith(Suite.class)
@SuiteClasses({
	PutLogEventTestCases.class
})

public class FunctionalTestSuite {

	@BeforeClass
	public static void initialiseSuite() {
		ConnectorTestContext.initialize(AWSCloudwatchConnector.class);
	}

	@AfterClass
	public static void shutdownSuite() {
		ConnectorTestContext.shutDown();
	}

}