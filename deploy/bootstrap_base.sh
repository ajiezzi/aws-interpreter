#!/bin/bash

# Copy AWS interpreter dependencies
aws s3 cp s3://zeppelin-emr-config/aws-interpreter-0.0.1-SNAPSHOT.jar .
aws s3 cp s3://zeppelin-emr-config/commons-exec-1.3.jar .