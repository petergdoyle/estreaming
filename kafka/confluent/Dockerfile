FROM estreaming/basejdk

RUN mkdir /confluent \
&& curl -O http://packages.confluent.io/archive/2.0/confluent-2.0.1-2.11.7.tar.gz \
&& tar -xvf confluent-2.0.1-2.11.7.tar.gz -C /confluent \
&& rm -f confluent-2.0.1-2.11.7.tar.gz \
&& ln -s /confluent/confluent-2.0.1/ /confluent/default

ENV CONFLUENT_HOME=/confluent/default

WORKDIR /confluent/default

CMD /bin/bash
