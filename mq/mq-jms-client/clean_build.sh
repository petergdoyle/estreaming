#!/bin/sh

mq_version='8'

. ./'mvn_install_mq'$mq_version'_client_jars.sh'

mvn clean install
