#!/bin/sh

cd cors_proxy
rm -fr node_modules
npm install
cd -

cd mongo_connect
rm -fr node_modules
npm install
cd -

cd streaming_api_client
rm -fr node_modules
npm install
cd -

cd streaming_api_server
rm -fr node_modules
npm install
cd -

cd twitter
rm -fr node_modules
npm install
cd -
