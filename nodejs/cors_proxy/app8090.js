var http = require('http'),
    httpProxy = require('http-proxy');

var proxy =  httpProxy.createProxyServer(
	{target:'http://localhost:9393'})
.listen(8090,'0.0.0.0');
console.log('Server running on port 8090');

proxy.on('proxyRes', function(proxyReq, req, res, options) {
  // add the CORS header to the response
    console.log(req.url);
    res.setHeader('Access-Control-Allow-Origin', '*');
});

proxy.on('error', function(e) {
  console.log(e);
}); 
