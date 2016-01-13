# aws-classiclink-lambda

This project contains an AWS Lambda function written in `java8` to enable ClassicLink on a VPC. The lambda function can be invoked through CloudFormation as a "custom resource". CloudFormation does not provide a way to do this natively yet other than through a custom resource. Perhaps AWS will add the support in the fututre. When they do add support for it, this project will not be as usefull. 

Although, there is another reason for creating it. And that is to a have a working example of how to write a lambda function in java. There are not that many examples of writing lambda function in java.  