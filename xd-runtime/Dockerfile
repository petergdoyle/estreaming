FROM estreaming/basejdk

RUN curl --insecure -O https://repo.spring.io/libs-release-local/org/springframework/xd/spring-xd/1.1.2.RELEASE/spring-xd-1.1.2.RELEASE-1.noarch.rpm \
&& yum -y localinstall spring-xd-1.1.2.RELEASE-1.noarch.rpm \
&& rm -fv spring-xd-1.1.2.RELEASE-1.noarch.rpm \
&& alternatives --install /usr/bin/xd-admin xd-admin /opt/pivotal/spring-xd/xd/bin/xd-admin 99999 \
&& alternatives --install /usr/bin/xd-container xd-container /opt/pivotal/spring-xd/xd/bin/xd-container 99999 \
&& alternatives --install /usr/bin/xd-singlenode xd-singlenode /opt/pivotal/spring-xd/xd/bin/xd-singlenode 99999

COPY xd/scripts/* /opt/pivotal/spring-xd/xd/modules/processor/scripts/
COPY xd/config/ /opt/pivotal/spring-xd/xd/config/

RUN ln -s /opt/pivotal/spring-xd/xd/config demo.xd-config \
&& ln -s /opt/pivotal/spring-xd/xd demo.xd-home \
&& ln -s /tmp/xd/output/ demo.xd-out \
&& ln -s /opt/pivotal/spring-xd/xd/modules/processor/scripts/ demo.xd-scripts

CMD /bin/bash
