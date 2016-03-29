#!/bin/sh


mq_dir=".."

version='8.0.0.0'

if [[ ! -d "$mq_dir/lib" ]]; then

  echo " no client libs avaiable. please run $mq_dir/docker_exec_cp_mq_client_jars.sh first"

else
    gid='com.ibm'
    aid="mq mq.allclient mq.defaultconfig  mq.commonservices mq.headers mq.jmqi mq.jms.Nojndi"

    for id in $aid
    do
      cmd="mvn install:install-file -Dfile=$mq_dir/lib/$gid.$id.jar \
      -DgroupId=$gid -DartifactId=$gid.$id -Dversion=$version -Dpackaging=jar"
      eval "$cmd"
    done

fi
