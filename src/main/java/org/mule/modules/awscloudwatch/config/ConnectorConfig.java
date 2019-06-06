package org.mule.modules.awscloudwatch.config;

import org.apache.log4j.Logger;
import org.mule.api.annotations.components.Configuration;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.TestConnectivity;
import org.mule.api.annotations.param.Default;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.logs.AWSLogsClient;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.DescribeLogStreamsRequest;
import com.amazonaws.services.logs.model.DescribeLogStreamsResult;

/**
 * 
 * The configuration class. This provides the config details as well as the test connectivity feature. In order to make the test connection works,
 * the log-group and the log-stream need to be created and should be accessible by the user which you retrieve the credentials.
 * @author Manoj LASANTHA
 */
@Configuration(friendlyName = "Configuration")
public class ConnectorConfig {

	/**
     * Key of AWS User credentials
     */ 
    @Configurable
    @Default("AWS Key ID")
    private String awsKey;

    /**
     * Secret of AWS User credentials
     */
    @Configurable
    @Default("AWS Key Secret")
    private String awsSecret;
    

    /**
     * Region of AWS account
     */
    @Configurable
    @Default("AWS Region")
    private String region;

    /**
     * Region of AWS account
     */
    @Configurable
    @Default("Cloudwatch log group name")
    private String logGroup;
    
    /**
     * Region of AWS account
     */
    @Configurable
    @Default("Cloudwatch log stream name")
    private String logStream;
    
    final static Logger logger = Logger.getLogger(ConnectorConfig.class); 
    
    
    @TestConnectivity
    public void testConnect() throws ConnectionException {
    	try {
            logger.error("Testing Connection: ");
            BasicAWSCredentials cred = new BasicAWSCredentials(this.awsKey, this.awsSecret);
    		AWSLogsClient client 		= (AWSLogsClient) AWSLogsClientBuilder
    				.standard()
    				.withCredentials(new AWSStaticCredentialsProvider(cred))
    				.withRegion(this.region)
    				.build();
    		//Sending a test request
    		DescribeLogStreamsRequest req = new DescribeLogStreamsRequest()
    				.withLogGroupName(this.logGroup)
    				.withLogStreamNamePrefix(this.logStream)
    		;
    		
    		DescribeLogStreamsResult result = client.describeLogStreams(req);
    		String seqToken = result.getLogStreams().get(0).getUploadSequenceToken();
         } catch (Exception e) {
             throw new ConnectionException(ConnectionExceptionCode.UNKNOWN, e.getMessage(), "Failed to connect to AWS. Please check your credentials.", e);
         }
    }
    
    /**
     * Set AWS Key
     *
     * @param awsKey the AWS Key ID
     */
    public void setAwsKey(String key) {
        this.awsKey = key;
    }

    /**
     * Get awsKey
     */
    public String getAwsKey() {
        return this.awsKey;
    }
    
    /**
     * Set AWS Secret
     *
     * @param awsSecret the AWS Secret
     */
    public void setAwsSecret(String secret) {
        this.awsSecret = secret;
    }

    /**
     * Get awsSecret
     */
    public String getAwsSecret() {
        return this.awsSecret;
    }
    
    /**
     * Set AWS Secret
     *
     * @param awsSecret the AWS Secret
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * Get awsSecret
     */
    public String getRegion() {
        return this.region;
    }
    
    /**
     * Set AWS Log group name
     *
     * @param logGroupName The AWS Log group name
     */
    public void setLogGroup(String logGroupName) {
        this.logGroup = logGroupName;
    }

    /**
     * Get AWS Log group name
     */
    public String getLogGroup() {
        return this.logGroup;
    }
    
    /**
     * Set AWS Log Stream name
     *
     * @param logStreamName The Log stream name
     */
    public void setLogStream(String logStreamName) {
        this.logStream = logStreamName;
    }

    /**
     * Get AWS log stream name
     */
    public String getLogStream() {
        return this.logStream;
    }  
}