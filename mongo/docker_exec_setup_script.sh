#!/bin/sh
docker exec -ti estreaming_mongodb_server 'mongo < /mongodb/setup.js'
