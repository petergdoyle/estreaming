FROM estreaming/basejdk

RUN mkdir /kafka \
&& curl -O http://apache.claz.org/kafka/0.10.0.0/kafka_2.11-0.10.0.0.tgz \
&& tar -xvf kafka_2.11-0.10.0.0.tgz -C /kafka \
&& rm -f kafka_2.11-0.10.0.0.tgz \
&& ln -s /kafka/kafka_2.11-0.10.0.0 /kafka/default

COPY config/ /kafka/default/config

# RUN wget -O jmxterm-1.0-alpha-4-uber.jar http://downloads.sourceforge.net/project/cyclops-group/jmxterm/1.0-alpha-4/jmxterm-1.0-alpha-4-uber.jar?r=https%3A%2F%2Fsourceforge.net%2Fprojects%2Fcyclops-group%2Ffiles%2Fjmxterm%2F1.0-alpha-4%2F

ENV KAFKA_HOME=/kafka/default

WORKDIR /kafka/default

CMD /bin/bash
