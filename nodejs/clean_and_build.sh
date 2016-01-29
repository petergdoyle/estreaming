#!/bin/sh

cd cors_proxy
rm -frv node_modules
npm install
cd -

cd mongo_connect
rm -frv node_modules
npm install
cd -

cd streaming_api_client
rm -frv node_modules
npm install
cd -

cd streaming_api_server
rm -frv node_modules
npm install
cd -

cd twitter
rm -frv node_modules
npm install
cd -
