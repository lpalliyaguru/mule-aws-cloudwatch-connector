package org.mule.modules.awscloudwatch.config;

import org.mule.api.annotations.components.Configuration;
import org.mule.api.annotations.Configurable;
import org.mule.api.annotations.param.Default;

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