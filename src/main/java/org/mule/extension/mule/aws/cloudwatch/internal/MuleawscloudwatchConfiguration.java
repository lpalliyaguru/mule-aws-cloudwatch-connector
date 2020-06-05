/*
 * Copyright (c) 2020.  The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),  a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.extension.mule.aws.cloudwatch.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations({ MuleawscloudwatchLogsOperations.class })
@ConnectionProviders( { MuleawscloudwatchConnectionProvider.class})
public class MuleawscloudwatchConfiguration {

}
