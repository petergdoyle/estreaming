Dev.Twitter.com
https://dev.twitter.com/streaming/overview
https://dev.twitter.com/overview/api/twitter-libraries
https://apps.twitter.com/app/8258889/keys to get my oath credentials

use spring xd to strart to stream tweets

introduce spring xd
http://docs.spring.io/spring-xd/docs/current-SNAPSHOT/reference/html/#introduction
it provides a streaming runtime for us
we have all these separate technologies and spring xd lets us manage them at runtime with a simple approach – it is set up with some fundamental components

``` console
xd:> stream create --name twittersearchjava --definition "twittersearch --consumerKey=toLroxf9t7WzsdQvVSFVQr23y --consumerSecret=WKYo5wjI2yGDqr0SmJyI5kUPGh5oIA4die6gmRYw4BZWIpFh2f --query='java' | file" –deploy
```

``` console
[vagrant@estreaming ~]$ cd demo.xd-out
[vagrant@estreaming ~]$ tail -f twittersearchjava.out
```


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
