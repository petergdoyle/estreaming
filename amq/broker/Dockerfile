FROM estreaming/basejdk

RUN curl -L -O http://apache.arvixe.com/activemq/5.13.2/apache-activemq-5.13.2-bin.tar.gz \
  && mkdir -p /activemq \
  && tar -xvf apache-activemq-5.13.2-bin.tar.gz -C /activemq \
  && ln -s /activemq/apache-activemq-5.13.2 /activemq/default \
  && rm -f apache-activemq-5.13.2-bin.tar.gz \
  && chmod 755 /activemq/default/bin/activemq

COPY conf/ /activemq/default/conf/

WORKDIR /activemq/default

RUN /bin/bash
