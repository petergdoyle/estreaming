#!/bin/sh
docker start estreaming_mongodb_server

docker logs -f estreaming_mongodb_server
