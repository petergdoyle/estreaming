

stream create car_echo_jms_to_redis --definition "jms --destination=car_availability | redis --queue=car_availability" --deploy


stream create car_echo_jms_to_log --definition "jms --destination=car_availability | log" --deploy
