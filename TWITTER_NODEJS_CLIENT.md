
Run the Twitter client Example to simple query the Twitter Stream and pull back results

To run the Node.js program switch directories
```console
[vagrant@estreaming ~]$ cd /vagrant/node/twitter
```

Query the Twitter Streaming API for 100 tweets that contain the word java
```console
[vagrant@estreaming ~]$ node twitter_streaming_client.js java 100
```

Kind of a mess right, so let's clean it up a little. So lets redirect the output to a file and then pretty print the JSON
```console
[vagrant@estreaming ~]$ node twitter_streaming_client.js java 100 > tweets_with_java.json
```

Now remove the first line which is part of the response and filter it through a json formatter. Notice we the pipeline metaphor again is so relevant in working with streams 
```bash
[vagrant@estreaming ~]$ node twitter_streaming_client.js java 100 | awk '{ if ( NR > 1  ) { print } }' | format-json-stream | less
```
