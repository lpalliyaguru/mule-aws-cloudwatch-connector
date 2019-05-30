
package org.mule.modules.awscloudwatch.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.devkit.ProcessAdapter;
import org.mule.api.devkit.ProcessTemplate;
import org.mule.api.processor.MessageProcessor;
import org.mule.api.routing.filter.Filter;
import org.mule.modules.awscloudwatch.AWSCloudwatchConnector;
import org.mule.security.oauth.callback.ProcessCallback;


/**
 * A <code>AWSCloudwatchConnectorProcessAdapter</code> is a wrapper around {@link AWSCloudwatchConnector } that enables custom processing strategies.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2019-05-30T02:27:08+08:00", comments = "Build UNNAMED.2793.f49b6c7")
public class AWSCloudwatchConnectorProcessAdapter
    extends AWSCloudwatchConnectorLifecycleInjectionAdapter
    implements ProcessAdapter<AWSCloudwatchConnectorCapabilitiesAdapter>
{


    public<P >ProcessTemplate<P, AWSCloudwatchConnectorCapabilitiesAdapter> getProcessTemplate() {
        final AWSCloudwatchConnectorCapabilitiesAdapter object = this;
        return new ProcessTemplate<P,AWSCloudwatchConnectorCapabilitiesAdapter>() {


            @Override
            public P execute(ProcessCallback<P, AWSCloudwatchConnectorCapabilitiesAdapter> processCallback, MessageProcessor messageProcessor, MuleEvent event)
                throws Exception
            {
                return processCallback.process(object);
            }

            @Override
            public P execute(ProcessCallback<P, AWSCloudwatchConnectorCapabilitiesAdapter> processCallback, Filter filter, MuleMessage message)
                throws Exception
            {
                return processCallback.process(object);
            }

        }
        ;
    }

}
