FROM estreaming/basejdk

RUN mkdir /kafka \
&& curl -O http://apache.arvixe.com/kafka/0.9.0.1/kafka_2.10-0.9.0.1.tgz \
&& tar -xvf kafka_2.10-0.9.0.1.tgz -C /kafka \
&& rm kafka_2.10-0.9.0.1.tgz \
&& ln -s /kafka/kafka_2.10-0.9.0.1 /kafka/default

COPY config/ /kafka/default/config

ENV KAFKA_HOME=/kafka/default

WORKDIR /kafka/default

CMD /bin/bash
