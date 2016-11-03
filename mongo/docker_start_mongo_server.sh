#!/bin/sh
docker start estreaming-mongodb-server

docker logs -f estreaming-mongodb-server
