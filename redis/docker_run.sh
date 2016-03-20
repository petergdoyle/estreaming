#!/bin/sh
docker run -ti \
  -d \
  --name estreaming_redis \
  --net host \
  estreaming/redis
  redis-server --appendonly yes
