#!/bin/sh
docker exec -ti estreaming-mongodb-server bash -c 'mongo < /mongodb/setup.js'
