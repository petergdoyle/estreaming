var express = require('express');
var router = express.Router();
var JSONStream = require('JSONStream');
var http = require('http'), queryString = require('querystring');


//mongojs stuff...
var mongojs = require('mongojs');
var url = 'mongodb://localhost:27017/airshop';
var db = mongojs(url, ['results']);
db.on('error',function(err) {
    console.log('database error', err);
});
db.on('ready',function() {
    console.log('database connected');
});


/* GET all results
router.get('/results', function(req, res, next) {

    var frame = req.query.frame;
    var fields = req.query.fields
    var qry = queryString.parse(req.url);
    console.log('query =',req.query);
    console.log('qry =',qry);
    console.log('url =',req.url)
    console.log('frame =',frame);
    console.log('fields =',fields);
    res.setHeader('content-type', 'application/json');
    res.connection.setTimeout(0);
    db.results
        .find({}, {}, {tailable:true, timeout:false})
	    .pipe(JSONStream.stringify())
	    .pipe(res);

});
*/

/* GET results starting at id specified ... */
router.get('/results/frame/:frame', function(req, res, next) {
    var frame = req.params.frame;
    var fieldFilter = {};
    var frameFilter = {};
    frameFilter['_id'] = { $gte: mongojs.ObjectId(frame)};
    res.setHeader('content-type', 'application/json');
    res.connection.setTimeout(0);
    db.results
        .find(frameFilter,
            fieldFilter,
            {tailable:true, timeout:false})
	    .pipe(JSONStream.stringify())
	    .pipe(res);
});


/* GET results starting at id specified ...*/
router.get('/results/', function(req, res, next) {

    var frame = req.query.frame;
    var fields = req.query.fields
    console.log('url =',req.url)
    console.log('frame=',frame);
    console.log('fields=',fields);

    var fieldFilter = {}; //
    if (fields) {
        fields.split(',').forEach(function(f) {
            fieldFilter['flight.'.concat(f)] = 1;
        });
    }

    var frameFilter = {};
    if (frame) {
        frameFilter['_id'] = { $gte: mongojs.ObjectId(frame)};
    }

    res.setHeader('content-type', 'application/json');
    res.connection.setTimeout(0);
    db.results
        .find(frameFilter, fieldFilter,
        {tailable:true, timeout:false})
	    .pipe(JSONStream.stringify())
	    .pipe(res);

});


module.exports = router;
