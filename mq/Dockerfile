FROM ibm/mq8

RUN sysctl fs.file-max=524288

RUN useradd vagrant -G mqm && \
    echo vagrant:passw0rd | chpasswd

COPY config_clauth_off.mqsc /etc/mqm/config.mqsc
