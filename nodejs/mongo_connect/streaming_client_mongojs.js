
var mongojs = require('mongojs');
var JSONStream = require('JSONStream');

var url = 'mongodb://localhost:27017/airshop';

var db = mongojs(url, ['results']);

//or ... create a cursor and use the console command
var cursor = db.results.find({}, {}, {tailable:true, timeout:false});

// since all cursors are streams we can just listen for data
cursor.on('data', function(doc) {
    console.log(doc);
});
