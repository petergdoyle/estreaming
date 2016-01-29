#!/bin/sh
docker start estreaming_nodejs_streaming_api_server

docker logs -f estreaming_nodejs_streaming_api_server
