
stream create --name airshop_stream --definition "jms --destination=airshop | transform --script=airshop_csv_to_json_transform.groovy | mongodb --databaseName=airshop --collectionName=results" --deploy


stream create --name splash_stream --definition "jms --destination=splash | mongodb --databaseName=splash --collectionName=solutions" --deploy



stream create kafka_file_stream --definition "kafka --zkconnect=localhost:2181 --topic=splash | file" --deploy
stream create kafka_mongo_stream --definition "kafka --zkconnect=localhost:2181 --topic=splash |mongodb --databaseName=splash --collectionName=solutions" --deploy
