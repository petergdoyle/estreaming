Create the Streaming API Client!
================================


While we can use a browser or a command line HTTP client utility like curl or wget to pull our streaming data, we want to provide a client specifically to connect to the server and stream to the console to abstract away the HTTP connection. In real life this client would do some other useful things like handle some type of authentication / authorization interaction between client and server, or it might add some failover or logging capabilities but for now, let's just keep it simple.

There is a prebuilt client program that will run with node.js. It is really pretty simple and without many features:

``` javascript
var http = require('http');

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

//curl http://localhost:3000/airshop/results

var options = {
  host: 'localhost',
  port: 3000,
  path: '/airshop/results',
  followAllRedirects: true
};

callback = function(response) {
  var str = '';

  //another chunk of data has been recieved, so append it to `str`
  response.on('data', function (chunk) {
    str += chunk;
  });

  //the whole response has been recieved, so we just print it out here
  response.on('end', function () {
    console.log(str);
  });
}

http.request(options, callback).end();

```

And to run it all you have to do is navigate to the right folder and launch it from node. So open up a terminal and try it.

``` console
[vagrant@estreaming ~]$ cd demo.node/streaming_api_client/
[vagrant@estreaming streaming_api_client]$ node app.js
```

And again you should see data streaming from the server
