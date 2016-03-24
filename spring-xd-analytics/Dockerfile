
FROM estreaming/basejdk

RUN yum -y install unzip \
&& curl -s get.sdkman.io | bash \
&& printf "sdkman_auto_answer=true" > /root/.sdkman/etc/config \
&& source "/root/.sdkman/bin/sdkman-init.sh" \
&& sdk install springboot \
&& sdk install groovy

COPY groovy/ groovy/

WORKDIR groovy/

CMD /bin/bash
