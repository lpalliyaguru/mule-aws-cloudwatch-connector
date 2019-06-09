/**
 * (c) 2018 INSEAD. The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.awscloudwatch.error;

import org.mule.api.annotations.Handle;
import org.mule.api.annotations.components.Handler;

@Handler
public class ErrorHandler {

    @Handle
    public void handle(Exception ex) throws Exception {
    	//TODO Process the exception
    }

}