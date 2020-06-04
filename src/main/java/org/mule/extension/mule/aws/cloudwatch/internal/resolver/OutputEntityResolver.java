package org.mule.extension.mule.aws.cloudwatch.internal.resolver;

import com.amazonaws.services.logs.model.FilterLogEventsResult;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.AttributesTypeResolver;
import org.mule.runtime.api.metadata.resolving.OutputTypeResolver;

public class OutputEntityResolver implements OutputTypeResolver<String>, AttributesTypeResolver<String> {
    @Override
    public String getCategoryName() {
        return "Records";
    }

    @Override
    public String getResolverName() {
        return "OutputEntityResolver";
    }
    @Override
    public MetadataType getOutputType(MetadataContext context, String key)
            throws MetadataResolvingException, ConnectionException {
       return context.getTypeLoader().load(FilterLogEventsResult.class);
    }

    @Override
    public MetadataType getAttributesType(MetadataContext context, String key)
            throws MetadataResolvingException, ConnectionException {



        // Only Books have Attributes information
        return context.getTypeBuilder().nullType().build();
    }
}
