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

## Installation and Configuration

To configure the AWS interpreter, follow the directions at http://zeppelin.apache.org/docs/latest/development/writingzeppelininterpreter.html#configure-your-interpreter .

## Usage

To utilize the AWS interpreter with your notebook, ensure the notebook is bound to the AWS interpreter using the setting icon on upper right corner of the notebook.

## Contributing
