#!/bin/sh
. ../../scripts/lib/docker_functions.sh
. ../../scripts/lib/network_functions.sh

start_cmd_min="java -Xms1G -Xmx1G \
-jar /activemq/default/bin/activemq.jar start"

start_cmd_max="java -Xms1G -Xmx1G \
-Djava.util.logging.config.file=logging.properties \
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote \
-Djava.io.tmpdir=/activemq/default/tmp \
-Dactivemq.classpath=/activemq/default/conf \
-Dactivemq.home=/activemq/default \
-Dactivemq.base=/activemq/default \
-Dactivemq.conf=/activemq/default/conf \
-Dactivemq.data=/activemq/default/data \
-jar /activemq/default/bin/activemq.jar start"

img_name='estreaming/activemq_server'
container_name='estreaming_activemq_server'

start_cmd=$start_cmd_min

network="$network_native"
docker_run 8161,61616
