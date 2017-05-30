# AWS Interpreter for Apache Zeppelin

The AWS Interpreter for Apache Zeppelin allows you to execute AWS CLI commands from a Zeppelin notebook as well as visualize the results.

The interpreter allows you to execute any AWS CLI command, but will currently only format the following commands into the table display:

```
%aws aws s3 ls
%aws aws s3 ls <bucket>
%aws aws s3api list-buckets
%aws aws s3api list-objects --bucket <bucket>
%aws aws workspaces describe-workspaces
```

## Building

At a minimum you will need a JVM and Maven installed. To build everything from the root directory of the project, execute the following command to build the source and package/copy the dependencies.

```
$ mvn clean install
```
Maven will create two Java artifacts, aws-interpreter-0.0.1-SNAPSHOT.jar and commons-exec-1.3.jar.

## Installation and Configuration

### EMR Configuration

The instructions assume both Java artifacts are already copied to the EMR master server. Zeppelin home will be found at `/usr/lib/zeppelin`. To configure the interpreter on EMR, you will need to follow these steps:

1. Create a new directory called `aws` under `interpreter/` 
2. Copy the two Java artifacts (aws-interpreter-0.0.1-SNAPSHOT.jar & commons-exec-1.3.jar) into the newly created directory
3. Add the AWS interpreter class `com.blacksky.command.AWSInterpreter` name to the zeppelin.interpreters property in `conf/zeppelin-site.xml`
4. Stop and start the Zeppelin service (e.g. `stop zeppelin` & `start zeppelin`)
5. In the interpreter web page (e.g. http://<host>/interpreter), click the Create button and configure the interpreter properties. Name should be `aws` and group should be `aws`. All other properties can be left as the defaults.

For additional information on how to configure the Zeppelin interpreters, follow the directions at http://zeppelin.apache.org/docs/latest/development/writingzeppelininterpreter.html#configure-your-interpreter .

## Usage

To utilize the AWS interpreter with your notebook, ensure the notebook is bound to the AWS interpreter using the setting icon on upper right corner of the notebook.

## Contributing
