#!/bin/sh

mvn -f redis-store-capped-sink/pom.xml clean install

cp -f redis-store-capped-sink/target/redis-store-capped-sink-1.0.0.BUILD-SNAPSHOT.jar ../xd-runtime/upload
