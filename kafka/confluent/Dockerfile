FROM estreaming/basejdk

RUN mkdir /confluent \
&& curl -O http://packages.confluent.io/archive/2.0/confluent-2.0.1-2.11.7.tar.gz \
&& tar -xvf confluent-2.0.1-2.11.7.tar.gz -C /confluent \
&& rm -f confluent-2.0.1-2.11.7.tar.gz \
&& ln -s /confluent/confluent-2.0.1/ /confluent/default

ENV CONFLUENT_HOME=/confluent/default

WORKDIR /confluent/default

COPY schema-registry.properties /confluent/default/etc/schema-registry/schema-registry.properties
COPY kafka-rest.properties /confluent/default/etc/kafka-rest/kafka-rest.properties

CMD /bin/bash
