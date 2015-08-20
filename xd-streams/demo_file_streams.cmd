
stream create --name jmstest --definition "jms --destination=airshop |file" --deploy

stream create --name jmstest_with_transform --definition "jms --destination=airshop | transform --script=airshop_csv_to_json_transform.groovy | fi
le" --deploy

stream create --name jms_payload_test --definition "jms --destination=airshop |transform --expression='[INFO] '+payload |file" --deploy
