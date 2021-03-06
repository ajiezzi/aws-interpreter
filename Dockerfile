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
MAINTAINER Adam Iezzi <aiezzi@blacksky.com>

# Default Environment Variables
ENV Z_HOME="/zeppelin" \
	AWS_DEFAULT_REGION="us-east-1" \
	AWS_DEFAULT_OUTPUT="json" \
    I_HOME="/aws-interpreter" \
    ZEPPELIN_VERSION="0.7.3"

# Install basic packages
RUN yum install -y \
	java-1.8.0-openjdk \
	wget \
	maven && \
	yum update -y

# Install apache zeppelin version
RUN wget -O /tmp/zeppelin-${ZEPPELIN_VERSION}-bin-netinst.tgz http://archive.apache.org/dist/zeppelin/zeppelin-${ZEPPELIN_VERSION}/zeppelin-${ZEPPELIN_VERSION}-bin-netinst.tgz && \
	tar -zxvf /tmp/zeppelin-${ZEPPELIN_VERSION}-bin-netinst.tgz && \
	rm -rf /tmp/zeppelin-${ZEPPELIN_VERSION}-bin-netinst.tgz && \
	mv /zeppelin-${ZEPPELIN_VERSION}-bin-netinst /zeppelin

# Install AWS CLI
RUN curl -O https://bootstrap.pypa.io/get-pip.py && \
	python get-pip.py && \
	pip install awscli

# Package up the interpreter
ADD pom.xml ${I_HOME}/pom.xml
ADD src ${I_HOME}/src
WORKDIR ${I_HOME}
RUN mvn clean install

# Install interpreter as a third-party interpreter
RUN mvn install:install-file -Dfile=./target/aws-interpreter-0.0.1-jar-with-dependencies.jar -DgroupId=com.blacksky -DartifactId=aws-interpreter -Dversion=0.0.1 -Dpackaging=jar && \
	/zeppelin/bin/install-interpreter.sh --name aws --artifact com.blacksky:aws-interpreter:0.0.1

EXPOSE 8080

WORKDIR ${Z_HOME}
CMD ["bin/zeppelin.sh"]