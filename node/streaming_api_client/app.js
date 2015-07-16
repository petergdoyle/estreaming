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
