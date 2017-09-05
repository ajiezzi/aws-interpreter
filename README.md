# AWS Interpreter for Apache Zeppelin

The AWS Interpreter for Apache Zeppelin allows you to execute AWS Command Line Interface (CLI) commands from a Zeppelin notebook as well as visualize the results. The AWS Command Line Interface is a unified tool to manage your AWS services.

The interpreter allows you to execute any AWS CLI command, but will currently only format JSON responses that are lists of JSON objects. Therefore, in order to have the output formatted, the following parameters should be used:

`--output json` - Formats the command output into valid JSON

`--query (string)` - A JMESPath query to use in filtering the response data. The parameter can be used to either filter or structure the reponse data into a list of JSON objects. Additional information can be found at http://jmespath.org/tutorial.html 

Below are examples of a valid CLI requests which can be added to a basic chart using Zeppelin's visualization:

```
"%table aws s3api list-objects --bucket test-bucket-001 --output json --query 'Contents[]'"

"%table aws ec2 describe-instances --filters 'Name=instance-state-name,Values=running,stopped' --query 'Reservations[].Instances[].{Id:InstanceId,Type:InstanceType,CreateDate:LaunchTime,State:State.Name}' --region us-east-1 --output json"

"%table aws s3api list-buckets --query 'Buckets[]' --output json"

```

For additional information, please check out the AWS CLI command reference: http://docs.aws.amazon.com/cli/latest/reference/index.html

## Building

At a minimum you will need a JVM and Maven installed. To build everything from the root directory of the project, execute the following command to build the source and package/copy the dependencies. The interpreter requires Java 1.8.

```
$ mvn clean install
```
Maven will create the a fat jar (e.g. aws-interpreter-0.0.1-SNAPSHOT-jar-with-dependencies.jar) which has all of the dependencies.

## Installation and Configuration

### EMR Configuration

The instructions assume both Java artifacts are already copied to the EMR master server. Zeppelin home will be found at `/usr/lib/zeppelin`. To configure the interpreter on EMR, you will need to follow these steps:

1. Create a new directory called `aws` under `interpreter/` 
2. Copy the Java artifact into the newly created directory
3. Add the AWS interpreter class `com.blacksky.command.AWSInterpreter` name to the zeppelin.interpreters property in `conf/zeppelin-site.xml`
4. Stop and start the Zeppelin service (e.g. `stop zeppelin` & `start zeppelin`)
5. In the interpreter web page (e.g. http://YOUR_DOMAIN_NAME/interpreter), click the Create button and configure the interpreter properties. Name should be `aws` and group should be `aws`. All other properties can be left as the defaults.

For additional information on how to configure the Zeppelin interpreters, follow the directions at http://zeppelin.apache.org/docs/latest/development/writingzeppelininterpreter.html#configure-your-interpreter .

## Usage

To utilize the AWS interpreter with your notebook, ensure the notebook is bound to the AWS interpreter using the setting icon on upper right corner of the notebook.

## Some Zeppelin eye candy

<p align="center">
	<a href="https://raw.githubusercontent.com/ajiezzi/aws-interpreter/master/docs/images/image_001.png" target="_blank"><img align="center" src="https://raw.githubusercontent.com/ajiezzi/aws-interpreter/master/docs/images/image_001.png" alt="Browse S3 buckets using Zeppelin"></a><br/><br/>
	<a href="https://raw.githubusercontent.com/ajiezzi/aws-interpreter/master/docs/images/image_002.png" target="_blank"><img align="center" src="https://raw.githubusercontent.com/ajiezzi/aws-interpreter/master/docs/images/image_002.png" alt="List and graph objects stored in S3 buckets using Zeppelin"></a><br/><br/>
</p>
