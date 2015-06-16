
var Twitter = require('twitter-node-client').Twitter;
//Callback functions
var error = function (err, response, body) {
    console.log('ERROR [%s]', err);
};
var success = function (data) {
    console.log(data);
};


//Get this data from your twitter apps dashboard
var config = {
    "consumerKey": "toLroxf9t7WzsdQvVSFVQr23y",
    "consumerSecret": "WKYo5wjI2yGDqr0SmJyI5kUPGh5oIA4die6gmRYw4BZWIpFh2f",
    "accessToken": "1665922212-bvs5aSBK5VFMyf6wzNskIWlWQlleqZVoTYO03Dd",
    "accessTokenSecret": "zI0C8sOo2VzsCDitEGBFMgaM1bPGagJX5rHfNo8ngyCnv"
}

var twitter = new Twitter(config);


//
// Get 10 tweets containing the hashtag haiku
//
// print process.argv


var myArgs = process.argv.slice(2);
var hashtag = myArgs[0];
var limit = myArgs[1];

twitter.getSearch({'q':hashtag,'count': limit}, error, success);
