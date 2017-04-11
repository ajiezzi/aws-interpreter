#!/bin/bash

IS_MASTER="false"
if [ -f /mnt/var/lib/info/instance.json ]
then
  IS_MASTER=`cat /mnt/var/lib/info/instance.json | tr -d '\n ' | sed -n 's|.*\"isMaster\":\([^,]*\).*|\1|p'`
fi

# Only runs on master node
if [ "$IS_MASTER" = "false" ]; then
  exit 0
fi

cd /home/hadoop
mkdir interpreter
cd interpreter

# Copy interpreter dependencies
aws s3 cp s3://zeppelin-emr-config-test/aws-interpreter-0.0.1-SNAPSHOT.jar .
aws s3 cp s3://zeppelin-emr-config-test/commons-exec-1.3.jar .