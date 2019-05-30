
package org.mule.modules.awscloudwatch.generated.adapters;

import java.util.List;
import javax.annotation.Generated;
import org.mule.common.DefaultResult;
import org.mule.common.Result;
import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataFailureType;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.property.StructureIdentifierMetaDataModelProperty;
import org.mule.devkit.internal.metadata.MetaDataGeneratorUtils;
import org.mule.modules.awscloudwatch.AWSCloudwatchConnector;
import org.mule.modules.awscloudwatch.config.ConnectorConfig;


/**
 * A <code>AWSCloudwatchConnectorConnectorConfigBasicAdapter</code> is a wrapper around {@link AWSCloudwatchConnector } that represents the strategy {@link ConnectorConfig }
 * 
 */
@SuppressWarnings("all")
@Generated(value = "Mule DevKit Version 3.9.0", date = "2019-05-30T02:27:08+08:00", comments = "Build UNNAMED.2793.f49b6c7")
public class AWSCloudwatchConnectorConnectorConfigBasicAdapter
    extends AWSCloudwatchConnectorProcessAdapter
    implements ConnectorMetaDataEnabled
{


    @Override
    public Result<List<MetaDataKey>> getMetaDataKeys() {
        try {
            return new DefaultResult<List<MetaDataKey>>(this.getLogTypes(), (Result.Status.SUCCESS));
        } catch (Exception e) {
            return new DefaultResult<List<MetaDataKey>>(null, (Result.Status.FAILURE), "There was an error retrieving the metadata keys from service provider after acquiring connection, for more detailed information please read the provided stacktrace", MetaDataFailureType.ERROR_METADATA_KEYS_RETRIEVER, e);
        }
    }

    @Override
    public Result<MetaData> getMetaData(MetaDataKey metaDataKey) {
        try {
            MetaData metaData = this.getMetadata(metaDataKey);
            metaData.getPayload().addProperty(new StructureIdentifierMetaDataModelProperty(metaDataKey, false));
            return new DefaultResult<MetaData>(metaData);
        } catch (Exception e) {
            return new DefaultResult<MetaData>(null, (Result.Status.FAILURE), MetaDataGeneratorUtils.getMetaDataException(metaDataKey), MetaDataFailureType.ERROR_METADATA_RETRIEVER, e);
        }
    }

}
