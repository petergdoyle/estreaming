
stream create --name airshop_stream --definition "jms --destination=airshop | transform --script=airshop_csv_to_json_transform.groovy | mongodb --databaseName=airshop --collectionName=results" --deploy
