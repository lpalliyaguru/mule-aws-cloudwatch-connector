
package org.mule.modules.awscloudwatch.generated.adapters;

import javax.annotation.Generated;
import org.mule.api.devkit.capability.Capabilities;
import org.mule.api.devkit.capability.ModuleCapability;
import org.mule.modules.awscloudwatch.AWSCloudwatchConnector;


/**
 * A <code>AWSCloudwatchConnectorCapabilitiesAdapter</code> is a wrapper around {@link AWSCloudwatchConnector } that implements {@link org.mule.api.Capabilities} interface.
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2019-05-30T02:27:08+08:00", comments = "Build UNNAMED.2793.f49b6c7")
public class AWSCloudwatchConnectorCapabilitiesAdapter
    extends AWSCloudwatchConnector
    implements Capabilities
{


    /**
     * Returns true if this module implements such capability
     * 
     */
    public boolean isCapableOf(ModuleCapability capability) {
        if (capability == ModuleCapability.LIFECYCLE_CAPABLE) {
            return true;
        }
        return false;
    }

}
