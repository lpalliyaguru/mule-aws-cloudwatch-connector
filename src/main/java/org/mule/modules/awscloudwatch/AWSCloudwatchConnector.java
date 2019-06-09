/**
 * (c) 2018 INSEAD. The software in this package is published under the terms of the Apache License, Version 2.0 (the "License"),
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.modules.awscloudwatch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.mule.api.annotations.Config;
import org.mule.api.annotations.Connector;
import org.mule.api.annotations.MetaDataKeyRetriever;
import org.mule.api.annotations.MetaDataRetriever;
import org.mule.api.annotations.Processor;
import org.mule.api.annotations.lifecycle.OnException;
import org.mule.api.annotations.lifecycle.Start;
import org.mule.api.annotations.param.MetaDataKeyParam;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.modules.awscloudwatch.config.ConnectorConfig;
import org.mule.modules.awscloudwatch.error.ErrorHandler;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.DescribeLogStreamsRequest;
import com.amazonaws.services.logs.model.DescribeLogStreamsResult;
import com.amazonaws.services.logs.model.InputLogEvent;
import com.amazonaws.services.logs.model.PutLogEventsRequest;
import com.amazonaws.services.logs.model.PutLogEventsResult;

/**
 * 
 * This is the connector class which contains the operations of the connector. This gets the configurations from the @Config 
 * annotation and use them accordingly for making connection to AWS with the help of AWS SDK
 * @author Manoj LASANTHA
 */
@Connector(name="aws-cloudwatch", friendlyName="AWS Cloudwatch")
@OnException(handler=ErrorHandler.class)
public class AWSCloudwatchConnector {

    @Config
    public ConnectorConfig config;
    
    private AWSLogsClient cwClient;
    private BasicAWSCredentials awsCreds;
    private String logStreamName;
    private String logStreamGroup;
    private volatile ArrayList<InputLogEvent> logEvents = new ArrayList();
    final static Logger logger = Logger.getLogger(AWSCloudwatchConnector.class);
 
    /**
     * Start the connector
     */
    @Start
    public void init() {
    	this.logStreamGroup = this.getConfig().getLogGroup();
    	this.logStreamName 	= this.getConfig().getLogStream();
    	this.awsCreds 		= new BasicAWSCredentials(this.getConfig().getAwsKey(), this.getConfig().getAwsSecret());
		this.cwClient 		= (AWSLogsClient) AWSLogsClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(this.awsCreds))
				.withRegion(this.getConfig().getRegion())
				.build();
    }
    
	/**
	* Send the log event to the CloudWatch
	*
	* @param message The log message to be sent to AWS CloudWatch
	* @param logType The type of the log message
	* @return return comment
	*/
	@Processor
	public boolean putLogEvent(@MetaDataKeyParam String logType, String message) {
		InputLogEvent logevent = new InputLogEvent().withMessage(getLoggableMessage(logType, message)).withTimestamp(System.currentTimeMillis());
		this.logEvents.add(logevent);
		PutLogEventsRequest request = new PutLogEventsRequest(this.logStreamGroup, this.logStreamName, this.logEvents)
				.withSequenceToken(this.getUploadSequenceToken());
		this.logEvents.removeAll(request.getLogEvents());
		PutLogEventsResult result = this.cwClient.putLogEvents(request);
        return result.getNextSequenceToken() != null;
	}
	
	/**
	 * 
	 * This method return the sequence token for the log Stream
	 * @return String
	 */
	private String getUploadSequenceToken() {
		DescribeLogStreamsRequest req = new DescribeLogStreamsRequest()
				.withLogGroupName(this.logStreamGroup)
				.withLogStreamNamePrefix(this.logStreamName)
		;
		
		DescribeLogStreamsResult result = this.cwClient.describeLogStreams(req);
		
		String seqToken = result.getLogStreams().get(0).getUploadSequenceToken();
		return seqToken;
	}
   
	/**
	 * Returns the connector configs
	 * @return {@link ConnectorConfig}
	 */
    public ConnectorConfig getConfig() {
        return config;
    }
    
    /**
     * 
     * @param config The configuration object
     */
    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }
    
    /**
     * The provider for the static log types. 
     * @return {@link DefaultMetaDataKey}
     */
    @MetaDataKeyRetriever
    public List<MetaDataKey> getLogTypes() {
    	List<MetaDataKey> logTypes = new ArrayList<MetaDataKey>();
    	logTypes.add(new DefaultMetaDataKey("INFO", "INFO"));
    	logTypes.add(new DefaultMetaDataKey("ERROR", "ERROR"));
    	logTypes.add(new DefaultMetaDataKey("WARNING", "WARNING"));
    	logTypes.add(new DefaultMetaDataKey("DEBUG", "DEBUG"));
    	
    	return logTypes;
    }
    
    /**
     * This returns the static type of the log event - INFO, ERROR, WARNING, DEBUG 
     * @param key {@link MetaDataKey}
     * @return {@link DefaultMetaData}
     * @throws Exception Throws an Runtime exception when the provided key is not in the supported list.
     */
    @MetaDataRetriever
    public MetaData getMetadata(MetaDataKey key) throws Exception { 
    	
    	if ("INFO".equals(key.getId())) {
    		MetaDataModel infoModel = new DefaultMetaDataBuilder().createDynamicObject("INFO").build();
    		return new DefaultMetaData(infoModel);
    	}
    	if ("ERROR".equals(key.getId())) {
    		MetaDataModel errorModel = new DefaultMetaDataBuilder().createDynamicObject("ERROR").build();
    		return new DefaultMetaData(errorModel);
    	}
    	if ("WARNING".equals(key.getId())) {
    		MetaDataModel warnModel = new DefaultMetaDataBuilder().createDynamicObject("WARNING").build();
    		return new DefaultMetaData(warnModel);
    	}
    	if ("DEBUG".equals(key.getId())) {
    		MetaDataModel debugModel = new DefaultMetaDataBuilder().createDynamicObject("DEBUG").build();
    		return new DefaultMetaData(debugModel);
    	}
    	
    	throw new RuntimeException(String.format("This entity %s is not supported", key.getId()));
    }
    
    private String getLoggableMessage(String type, String message) {
   
    	return String.format(
    			"%s [%s] %s", 
    			(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z")).format(new Date()), 
    			type, 
    			message 
    	);
    }
}