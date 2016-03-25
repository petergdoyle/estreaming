#!/bin/sh
status=$(git status --porcelain .)
if [[ "$status" != "" || ! -d "target" ]]; then
  mvn clean install
fi
