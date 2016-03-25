#!/bin/sh


cd cors_proxy
status=$(git status --porcelain .)
if [[ "$status" != "" || ! -d "node_modules" ]]; then
    rm -fr node_modules
    npm install
fi
cd -

cd mongo_connect
status=$(git status --porcelain .)
if [[ "$status" != "" || ! -d "node_modules" ]]; then
  rm -fr node_modules
  npm install
fi
cd -

cd streaming_api_client
status=$(git status --porcelain .)
if [[ "$status" != "" || ! -d "node_modules" ]]; then
  rm -fr node_modules
  npm install
fi
cd -

cd streaming_api_server
status=$(git status --porcelain .)
if [[ "$status" != "" || ! -d "node_modules" ]]; then
  rm -fr node_modules
  npm install
fi
cd -

cd twitter
status=$(git status --porcelain .)
if [[ "$status" != "" || ! -d "node_modules" ]]; then
  rm -fr node_modules
  npm install
fi
cd -
