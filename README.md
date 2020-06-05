## AWS Cloudwatch Anypoint Connector

This connector was built in the absence of AWS cloudwatch connector in the marketplace. I have had several use cases where we need to publish logs to AWS Cloudwatch for longer retentions, better search and better analysis.

In this version of connector allows users to stream logs to AWS Cloudwatch log streams. 

We are planning to add other sub-services of AWS Cloudwatch as connector operations in the coming releases. 

The connector uses [AWS Java SDK 1.x](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/index.html) version to connect to AWS Cloudwatch service. 

## Mule supported versions

Mule 3.9.x

## Why this Cloudwatch connector 
Mostly, we have use cases as mentioned above - to send logs to another logging platform.
Other alternative is use a log appender such as [logback](https://logback.qos.ch/). But, in this case, you will not be able to see logs in the Cloudhub. The logs are sent to the Cloudwatch as entire log set. 
If you have a case where you want to send only desired logs which happen on business events, thig might be an ideal connector to use with. 

## Releases
Please see the [Release notes](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/blob/4.x/doc/release-notes.adoc)

## Java Documentation
Visit the [Documentation](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/blob/4.x/doc/user-manual.adoc)

# Manual 
Visit the [User manual](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/blob/4.x/doc/user-manual.adoc)

# Reporting Issues 
:construction:
We use GitHub:Issues for tracking issues with this connector. You can report new issues at this link https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/issues.

