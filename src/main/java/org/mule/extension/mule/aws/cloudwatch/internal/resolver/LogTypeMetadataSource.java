package org.mule.extension.mule.aws.cloudwatch.internal.resolver;

import org.mule.runtime.api.exception.MuleException;
import org.mule.runtime.extension.api.annotation.metadata.MetadataKeyId;
import org.mule.runtime.extension.api.annotation.metadata.MetadataScope;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.mule.runtime.extension.api.runtime.source.Source;
import org.mule.runtime.extension.api.runtime.source.SourceCallback;

import java.util.Map;

@MetadataScope(keysResolver = LogTypeKeyResolver.class)
public class LogTypeMetadataSource extends Source<Map<String, Object>, Void> {

    @Parameter
    @MetadataKeyId
    @DisplayName("Log Type")
    private String logType;

    public LogTypeMetadataSource(String logType) {
        this.logType = logType;
    }
    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    @Override
    public void onStart(SourceCallback<Map<String, Object>, Void> sourceCallback) throws MuleException {

    }

    @Override
    public void onStop() {

    }
}
