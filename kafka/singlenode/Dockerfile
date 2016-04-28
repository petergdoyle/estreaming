FROM estreaming/basejdk

RUN mkdir /usr/kafka \
&& curl -O http://apache.claz.org/kafka/0.9.0.1/kafka_2.11-0.9.0.1.tgz \
&& tar -xvf kafka_2.11-0.9.0.1.tgz -C /usr/kafka \
&& rm -f kafka_2.11-0.9.0.1.tgz \
&& ln -s /usr/kafka/kafka_2.11-0.9.0.1 /usr/kafka/default

COPY config/ /kafka/default/config

ENV KAFKA_HOME=/kafka/default

WORKDIR /kafka/default

CMD /bin/bash
