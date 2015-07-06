var http = require('http'),
    httpProxy = require('http-proxy');

var proxy =  httpProxy.createProxyServer(
	{target:'http://localhost:9889'})
.listen(8080,'0.0.0.0');
console.log('Server running on port 8080');

proxy.on('proxyRes', function(proxyReq, req, res, options) {
  // add the CORS header to the response
    console.log(req.url);
    res.setHeader('Access-Control-Allow-Origin', '*');
});

proxy.on('error', function(e) {
  console.log(e);
}); 
