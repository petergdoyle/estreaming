FROM estreaming/basejdk

COPY target/spring-xd-jms-sender-1.0-SNAPSHOT.jar /activemq/spring-xd-jms-sender-1.0-SNAPSHOT.jar
COPY target/lib /activemq/lib/

WORKDIR /activemq

RUN /bin/bash
