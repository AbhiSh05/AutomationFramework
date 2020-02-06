#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY . /home/app/
RUN mvn clean test -f /home/app/pom.xml -D SuiteXmlFile=/home/app/TestSuite/testsuite.xml
