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
Please see the [release notes](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/releases)

## Java Documentation
Visit the [docs](https://lpalliyaguru.github.io/mule-aws-cloudwatch-connector/)

# Installation 
### 1. Build yourself
 :muscle:
You can download the source code and build it with devkit to find it available on your local repository. Then you can add it to Studio. 
(You can find the steps in https://docs.mulesoft.com/connector-devkit/3.9/)

### 2. Manually add the connector to your studio 
:hammer:
1. Open studio. Go to Help -> Install new software
2. Click "Add" button. 
3. In the "Add Repository" dialog box, click on the  "Archive" button
4. Choose the "UpdateSte.zip" (You can download from [here](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/raw/master/demo/UpdateSite.zip))
5. Please note that there will be several warning from the studio when you do this installation. As this is beta version of the connector, we have not sign the package yet. You can ignore those warnings. 

# Usage 
:ledger:
1. After you install the connector, it will appear in your mule pallete.
![](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/raw/master/images/in-mule-pallete.PNG)
2. Simply drag and drop the connector in to the canvas. 
3. After that you need to configure the connector. 
4. Click on the connector and go to the Mule Properties tab
![](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/raw/master/images/in-config-pallete.PNG)
5. Click on the add icon in front of the "Connector Configurations" drop down
6. Enter correct configuration details in the fields. Normally, it is recommended to create a IAM Service user in your aws account and assing only cloudwatch access. If needed, provide only access to the particular log group and stream. 
![](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/raw/master/images/in-config-pallete.PNG)
7. Also, it is recommended to configure the credentials in the properties and user the as reference in these fields. 
![](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/raw/master/images/test-connection.PNG)
8. Click on the "Test Connection" button. If all configs are correct, the connection should successful. 
![](https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/raw/master/images/connection-success.PNG)



# Reporting Issues 
:construction:
We use GitHub:Issues for tracking issues with this connector. You can report new issues at this link https://github.com/lpalliyaguru/mule-aws-cloudwatch-connector/issues.

