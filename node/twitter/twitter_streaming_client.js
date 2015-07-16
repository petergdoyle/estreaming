
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
    "consumerKey": "*",
    "consumerSecret": "*",
    "accessToken": "*-*",
    "accessTokenSecret": "*"
}

var twitter = new Twitter(config);


//
// Get 10 tweets containing the hashtag haiku
//
// print process.argv


var myArgs = process.argv.slice(2);
if (!myArgs || myArgs.length<2) {
	console.log("usage: node twitter_streaming_api_client.js \"#{hashtag}\" {limit} \nwhere {hashtag} is the tag you want to search for and {limit} is the number of records to pull from the stream");
	return;
}
var hashtag = myArgs[0];
var limit = myArgs[1];

twitter.getSearch({'q':hashtag,'count': limit}, error, success);
