#!/bin/sh
docker exec -ti estreaming-mongodb-server 'mongo < /mongodb/setup.js'
