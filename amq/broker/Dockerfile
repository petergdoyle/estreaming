FROM estreaming/basejdk

RUN curl -L -O http://archive.apache.org/dist/activemq/5.13.3/apache-activemq-5.13.3-bin.tar.gz \
  && mkdir -p /activemq \
  && tar -xvf apache-activemq-5.13.3-bin.tar.gz -C /activemq \
  && ln -s /activemq/apache-activemq-5.13.3 /activemq/default \
  && rm -f apache-activemq-5.13.3-bin.tar.gz \
  && chmod 755 /activemq/default/bin/activemq

COPY conf/ /activemq/default/conf/

WORKDIR /activemq/default

RUN /bin/bash
