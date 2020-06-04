package org.mule.extension.mule.aws.cloudwatch.internal.resolver;

import org.mule.metadata.api.builder.ObjectTypeBuilder;
import org.mule.metadata.api.model.MetadataType;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.FailureCode;
import org.mule.runtime.api.metadata.resolving.InputTypeResolver;

public class InputLogTypeResolver implements InputTypeResolver<String> {

    @Override
    public MetadataType getInputMetadata(MetadataContext metadataContext, String key) throws MetadataResolvingException, ConnectionException {
        final ObjectTypeBuilder objectTypeBuilder = metadataContext.getTypeBuilder().objectType();

        switch (key) {
            case "INFO":
                objectTypeBuilder.addField().key("INFO").value().stringType();
                break;
            case "DEBUG":
                objectTypeBuilder.addField().key("DEBUG").value().stringType();
                break;
            case "WARN":
                objectTypeBuilder.addField().key("WARN").value().stringType();
                break;
            case "ERROR":
                objectTypeBuilder.addField().key("ERROR").value().stringType();
                break;
            default:
                throw new MetadataResolvingException("Unknown key: " + key, FailureCode.INVALID_METADATA_KEY);
        }

        return objectTypeBuilder.build();
    }

    @Override
    public String getCategoryName() {
        return "LogTypesEntities";
    }
}
