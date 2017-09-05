# Zeppelin, a web-based notebook that enables interactive data analytics. 
# You can make beautiful data-driven, interactive and collaborative 
# documents with SQL, Scala and more. 
#
# The third-party AWS interpreter enables an end-user to execute and 
# display AWS CLI commands from an Apache Zeppelin notebook. Additional
# information can be found at https://github.com/ajiezzi/aws-interpreter
# 
# In order to run the docker image successfully, you must posses AWS 
# API keys and pass the keys into the docker container as environment 
# variables. An simple example run command is below:
#
# docker run --rm -p 8080:8080 -e AWS_ACCESS_KEY_ID='xxxxx' \
#  -e AWS_SECRET_ACCESS_KEY='xxxx' --name zeppelin adamiezzi/aws-zeppelin
FROM centos:7
MAINTAINER Adam Iezzi <aiezz@blacksky.com>

# Default Environment Variables
ENV AWS_ACCESS_KEY_ID="" \
	AWS_SECRET_ACCESS_KEY="" \
	AWS_DEFAULT_REGION="us-east-1" \
	AWS_DEFAULT_OUTPUT="json" \
    Z_HOME="/zeppelin"

# Install required packages
RUN yum install -y epel-release && \
	yum update -y && \
	yum install -y \
	java-1.8.0-openjdk \
	wget \
	maven

# Install Apache Zeppelin version 0.7.2
RUN wget -O /tmp/zeppelin-0.7.2-bin-netinst.tgz http://archive.apache.org/dist/zeppelin/zeppelin-0.7.2/zeppelin-0.7.2-bin-netinst.tgz && \
	tar -zxvf /tmp/zeppelin-0.7.2-bin-netinst.tgz && \
	rm -rf /tmp/zeppelin-0.7.2-bin-netinst.tgz && \
	mv /zeppelin-0.7.2-bin-netinst /zeppelin

# Install AWS CLI
RUN curl -O https://bootstrap.pypa.io/get-pip.py && \
	python get-pip.py && \
	pip install awscli
	
# Install AWS interpreter as a third-party interpreter
RUN wget -O /tmp/aws-interpreter-0.0.1.jar http://s3.amazonaws.com/cloudcover-artifacts/aws-interpreter-0.0.1.jar && \
	mvn install:install-file -Dfile=/tmp/aws-interpreter-0.0.1.jar -DgroupId=com.blacksky -DartifactId=aws-interpreter -Dversion=0.0.1 -Dpackaging=jar && \
	/zeppelin/bin/install-interpreter.sh --name aws --artifact com.blacksky:aws-interpreter:0.0.1

EXPOSE 8080

WORKDIR ${Z_HOME}
CMD ["bin/zeppelin.sh"]