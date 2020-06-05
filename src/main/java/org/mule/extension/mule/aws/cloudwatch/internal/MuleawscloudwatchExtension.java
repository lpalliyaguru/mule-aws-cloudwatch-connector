/*
 * Copyright (c) 2020.  The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),  a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.extension.mule.aws.cloudwatch.internal;

import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;


/**
 * This is the main class of an extension, is the entry point from which configurations, connection providers, operations
 * and sources are going to be declared.
 */
@Xml(prefix = "mule-aws-cloudwatch")
@Extension(name = "Amazon Cloudwatch", vendor = "INSEAD")
@Configurations({MuleawscloudwatchConfiguration.class})
public class MuleawscloudwatchExtension {

}
