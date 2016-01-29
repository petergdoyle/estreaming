#!/bin/sh


read -e -p "Enter the hashtag to search on: " -i "trump" hashtag
read -e -p "Enter the maximum records to return: " -i "100" record_limit

node twitter_streaming_client.js '"#{'$hashtag'}"' 100 | awk '{ if ( NR > 1  ) { print } }' |format-json-stream| less 
