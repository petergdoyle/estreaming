

[ -d lib ] || mkdir lib \

#basically copy the mq client jars up the the lib directory from the running container image, if they don't already exist and exclude the osgi ones.
docker exec estreaming_ibm_mq8_broker find opt/mqm/java/lib/ -name com.ibm.mq.*jar -type f \( ! -name "*osgi*" \) -exec cp --no-clobber {} /docker/mq-jms-client/lib \;
