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

@Connector(name="aws-cloudwatch", friendlyName="AWS Cloudwatch")
@OnException(handler=ErrorHandler.class)
public class AWSCloudwatchConnector {

    @Config
    ConnectorConfig config;
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
	* Logging message to the CloudWatch
	*
	* @param message The log message to be sent to AWS CloudWatch
	* @param type The type of the log message
	* @return return comment
	*/
	@Processor
	public String log(@MetaDataKeyParam String logType, String message) {
		InputLogEvent logevent = new InputLogEvent().withMessage(getLoggableMessage(logType, message)).withTimestamp(System.currentTimeMillis());
		this.logEvents.add(logevent);
		PutLogEventsRequest request = new PutLogEventsRequest(this.logStreamGroup, this.logStreamName, this.logEvents)
				.withSequenceToken(this.getUploadSequenceToken());
		this.logEvents.removeAll(request.getLogEvents());
		PutLogEventsResult result = this.cwClient.putLogEvents(request);
        return result.toString();
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
   
    public ConnectorConfig getConfig() {
        return config;
    }

    public void setConfig(ConnectorConfig config) {
        this.config = config;
    }
    
    @MetaDataKeyRetriever
    public List<MetaDataKey> getLogTypes() throws Exception{
    	List<MetaDataKey> logTypes = new ArrayList<MetaDataKey>();
    	logTypes.add(new DefaultMetaDataKey("INFO", "INFO"));
    	logTypes.add(new DefaultMetaDataKey("ERROR", "ERROR"));
    	logTypes.add(new DefaultMetaDataKey("WARNING", "WARNING"));
    	logTypes.add(new DefaultMetaDataKey("DEBUG", "DEBUG"));
    	
    	return logTypes;
    }
    
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