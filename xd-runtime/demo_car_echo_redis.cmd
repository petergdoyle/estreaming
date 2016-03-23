

stream create car_echo_jms_to_redis --definition "jms --destination=car_availability | redis --queue=car_availability" --deploy

stream create car_echo_rpush --definition "jms --destination=car | redis --leftPush=false --queue=car" --deploy

stream create car_echo_jms_to_log --definition "jms --destination=car_availability | log" --deploy



module delete sink:redis-store-capped

module upload --file /upload/rredis-store-capped-sink-1.0.0.BUILD-SNAPSHOT.jar --name redis-store-capped --type sink

stream create car_echo_jms_to_redis --definition "jms --destination=car | redis-store-capped" --deploy
