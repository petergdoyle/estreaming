
var mongojs = require('mongojs');
var JSONStream = require('JSONStream');
var http = require('http');

var url = 'mongodb://localhost:27017/airshop';

var db = mongojs(url, ['results']);

db.on('error',function(err) {
    console.log('database error', err);
});

db.on('ready',function() {
    console.log('database connected');
});

// tail to http response stream
var server = http.createServer(function (req, res) {
    res.setHeader('content-type', 'application/json');
    res.connection.setTimeout(0);
    db.results
        .find({}, {}, {tailable:true, timeout:false})
	    .pipe(JSONStream.stringify())
	    .pipe(res);

});

server.listen(8090);
console.log('server listening on http://localhost:8090');
