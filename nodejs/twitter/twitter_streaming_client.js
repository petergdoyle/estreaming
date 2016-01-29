
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
    "consumerKey": "Br0f1MmM7zsoAxnsBrhpGMNhS",
    "consumerSecret": "gCN02L4k4M4VF2ZbTLiyaK3ZPIcOhSgWYOC2bNZHcrjri3jmRF",
    "accessToken": "1665922212-bvs5aSBK5VFMyf6wzNskIWlWQlleqZVoTYO03Dd",
    "accessTokenSecret": "zI0C8sOo2VzsCDitEGBFMgaM1bPGagJX5rHfNo8ngyCnv"

}

var twitter = new Twitter(config);


//
// Get 10 tweets containing the hashtag haiku
//
// print process.argv


var myArgs = process.argv.slice(2);
if (!myArgs || myArgs.length<2) {
	console.log("usage: node twitter_streaming_api_client.js \"#{hashtag}\" {limit} \nwhere {hashtag} is the tag you want to search for and {limit} is the number of records to pull from the stream");
  console.log(myArgs);
  console.log(myArgs.length<2);
  console.log(myArgs);
  return;
}
var hashtag = myArgs[0];
var limit = myArgs[1];

twitter.getSearch({'q':hashtag,'count': limit}, error, success);
