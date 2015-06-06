
var mongojs = require('mongojs');
var JSONStream = require('JSONStream');

var url = 'mongodb://localhost:27017/airshop';

var db = mongojs(url, ['results']);

db.on('error',function(err) {
    console.log('database error', err);
});

db.on('ready',function() {
    console.log('database connected');
});

// pipe to stdout
//db.results.find({}).pipe(JSONStream.stringify()).pipe(process.stdout);


//or ... create a cursor and use the console command
var cursor = db.results.find({}, {}, {tailable:true, timeout:false});

// since all cursors are streams we can just listen for data
cursor.on('data', function(doc) {
    console.log(doc);
});
