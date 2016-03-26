#!/bin/sh



if [[ ! -d "lib" ]]; then
  tar -xvf com-ibm-mq.tar.bz2

  if [ $? -eq 0 ]; then

    mvn install:install-file -Dfile=lib/com.ibm.disthub2-7.0.0.jar \
    -DgroupId=com.ibm.disthub2 -DartifactId=com.ibm.disthub2 -Dversion=7.0.0 -Dpackaging=jar

    mvn install:install-file -Dfile=lib/com.ibm.mq-7.5.0.5.jar \
    -DgroupId=com.ibm -DartifactId=com.ibm.mq -Dversion=7.5.0.5 -Dpackaging=jar

    mvn install:install-file -Dfile=lib/com.ibm.mq.commonservices-7.5.0.5.jar \
    -DgroupId=com.ibm -DartifactId=com.ibm.mq.commonservices -Dversion=7.5.0.5 -Dpackaging=jar

    mvn install:install-file -Dfile=lib/com.ibm.mq.connector-7.5.0.5.jar \
    -DgroupId=com.ibm -DartifactId=com.ibm.mq.connector -Dversion=7.5.0.5 -Dpackaging=jar

    mvn install:install-file -Dfile=lib/com.ibm.mq.headers-7.5.0.5.jar \
    -DgroupId=com.ibm -DartifactId=com.ibm.mq.headers -Dversion=7.5.0.5 -Dpackaging=jar

    mvn install:install-file -Dfile=lib/com.ibm.mq.jmqi-7.5.0.5.jar \
    -DgroupId=com.ibm -DartifactId=com.ibm.mq.jmqi -Dversion=7.5.0.5 -Dpackaging=jar

    mvn install:install-file -Dfile=lib/com.ibm.mqjms-7.5.0.5.jar \
    -DgroupId=com.ibm -DartifactId=com.ibm.mqjms -Dversion=7.5.0.5 -Dpackaging=jar

    mvn install:install-file -Dfile=lib/com.ibm.mq.pcf-7.5.0.5.jar \
    -DgroupId=com.ibm -DartifactId=com.ibm.mq.pcf -Dversion=7.5.0.5 -Dpackaging=jar

    mvn install:install-file -Dfile=lib/com.ibm.mq.pcf-7.5.0.5.jar \
    -DgroupId=javax.jms -DartifactId=jms -Dversion=1.1 -Dpackaging=jar

  fi

fi
