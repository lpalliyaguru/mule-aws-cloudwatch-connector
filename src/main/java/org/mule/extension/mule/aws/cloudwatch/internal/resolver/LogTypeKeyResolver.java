package org.mule.extension.mule.aws.cloudwatch.internal.resolver;

import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.MetadataContext;
import org.mule.runtime.api.metadata.MetadataKey;
import org.mule.runtime.api.metadata.MetadataKeyBuilder;
import org.mule.runtime.api.metadata.MetadataResolvingException;
import org.mule.runtime.api.metadata.resolving.TypeKeysResolver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogTypeKeyResolver implements TypeKeysResolver {
    @Override
    public String getCategoryName() {
        return "LogTypes";
    }

    @Override
    public Set<MetadataKey> getKeys(MetadataContext metadataContext) throws MetadataResolvingException, ConnectionException {
        List<String> keyIds         = Arrays.asList("INFO_id", "WARN_id", "DEBUG_id", "ERROR_id");
        HashSet<MetadataKey> types  = new HashSet<>();

        for (String id: keyIds) {
            MetadataKeyBuilder builder = MetadataKeyBuilder.newKey(id);
            builder.withDisplayName(StringUtils.removeEnd(id, "_id"));
            types.add(builder.build());
        }
        return types;
    }
}
