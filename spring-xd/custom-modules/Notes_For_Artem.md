##Goal
Use Spring XD to read arbitrary data from an supported xd "source' and pipe the data into an xd Redis "sink" that is bounded to an arbitrary size. While Redis not specify such a collection type, it can be achieved as describe here under [Capped Lists](http://redis.io/topics/data-types-intro#capped-lists). For reference, this is much the same desired functionality that is achieved using a so-called Mongo [Capped Collection](https://docs.mongodb.org/manual/core/capped-collections/) except that using the Redis sink will provide a "buffer" and "framing" mechanism to streaming conusmers where the data format need not be from a native JSON source nor a source format converted (using perhaps an xd transformer) to JSON. While capped collections in Mongo are quite useful and provides additional functionality beyond a  Redis collection, that is not always possible or necessary for non-JSON payloads. 

##Redis Behavior
Since it is indicated that the RedisQueueOutboundChannelAdapter by default, is using a "leftPush" on the call to the RedisTemplate, the expected behavior then of the adapter would follow the behavior those commands lpush commands would work in Redis using redis-cli. 
```bash
127.0.0.1:6379> lpush collection 1
(integer) 1
127.0.0.1:6379> lpush collection 2
(integer) 2
127.0.0.1:6379> lpush collection 3
(integer) 3
127.0.0.1:6379> lpush collection 4
(integer) 4
127.0.0.1:6379> lpush collection 5
(integer) 5
127.0.0.1:6379> lpush collection 6
(integer) 6
127.0.0.1:6379> lrange collection 0 -1
1) "6"
2) "5"
3) "4"
4) "3"
5) "2"
6) "1"
```
The RedisQueueOutboundChannelAdapter seems to behave the expected way given a stream with an http source and redis sink
```bash
xd:>stream create --name httptest --definition "http |redis --queue=httptest" --deploy
Created and deployed new stream 'httptest'
xd:>stream list
  Stream Name            Stream Definition                                           Status
  ---------------------  ----------------------------------------------------------  ----------
  httptest               http |redis --queue=httptest                                deployed

xd:>http post --target http://localhost:9000 --data "1"
> POST (text/plain;Charset=UTF-8) http://localhost:9000 1
> 200 OK

xd:>http post --target http://localhost:9000 --data "2"
> POST (text/plain;Charset=UTF-8) http://localhost:9000 2
> 200 OK

xd:>http post --target http://localhost:9000 --data "3"
> POST (text/plain;Charset=UTF-8) http://localhost:9000 3
> 200 OK

xd:>http post --target http://localhost:9000 --data "4"
> POST (text/plain;Charset=UTF-8) http://localhost:9000 4
> 200 OK

xd:>http post --target http://localhost:9000 --data "5"
> POST (text/plain;Charset=UTF-8) http://localhost:9000 5
> 200 OK

xd:>http post --target http://localhost:9000 --data "6"
> POST (text/plain;Charset=UTF-8) http://localhost:9000 6
> 200 OK
```
And checking that in Redis confirms we are indeed pushing from the left with the oldest item at the end of the list. ->6->5->4->3->2->1
```bash
127.0.0.1:6379> lrange httptest 0 -1
1) "6"
2) "5"
3) "4"
4) "3"
5) "2"
6) "1"

```

Now the question becomes can we cap the list at a specific size so oldest elements drop off as new ones are added? Let's say we want a cap of 5 elements, that would mean we could use a redis ltrim command to ```retain``` only the elements from index 0 to 4 (or a maximum collection size of 5). 

```bash
127.0.0.1:6379> lrange httptest 0 -1
1) "6"
2) "5"
3) "4"
4) "3"
5) "2"
6) "1"
127.0.0.1:6379> ltrim httptest 0 4
OK
127.0.0.1:6379> lrange httptest 0 -1
1) "6"
2) "5"
3) "4"
4) "3"
5) "2"

```
So that works and we drop last element after the last element is added and keeps it trimmed to size on repeated executions
```bash
127.0.0.1:6379> ltrim httptest 0 4
OK
127.0.0.1:6379> lrange httptest 0 -1
1) "6"
2) "5"
3) "4"
4) "3"
5) "2"
```
And as new items are added to the collection, and the same ```ltrim``` command is called after each insertion (```lpush```) we drop the items indexed beyond the range of the cap.
```bash
127.0.0.1:6379> lpush httptest 7
(integer) 6
127.0.0.1:6379> lrange httptest 0 -1
1) "7"
2) "6"
3) "5"
4) "4"
5) "3"
6) "2"
127.0.0.1:6379> ltrim httptest 0 4
OK
127.0.0.1:6379> lrange httptest 0 -1
1) "7"
2) "6"
3) "5"
4) "4"
5) "3"

```


##Suggested Approach To Implement Desired Behavior 
Create a custom spring-xd sink like the one outlined in the [documentation](http://docs.spring.io/spring-xd/docs/current/reference/html/#creating-a-sink-module) referred to as redis-sink. This seems to leverage the existing RedisQueueOutboundChannelAdapter without having to start from scratch. 

The customized redis-sink source is located here [git](https://github.com/petergdoyle/estreaming/tree/master/spring-xd-samples-min)
```html
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:int="http://www.springframework.org/schema/integration"
       xmlns:int-redis="http://www.springframework.org/schema/integration/redis"
       xmlns:beans="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/integration
		http://www.springframework.org/schema/integration/spring-integration.xsd
		http://www.springframework.org/schema/integration/redis
		http://www.springframework.org/schema/integration/redis/spring-integration-redis.xsd">

    <int:channel id="input" />
    
    <int-redis:store-outbound-channel-adapter
        id="redisListAdapter" collection-type="LIST" channel="input"
        key="${collection}" auto-startup="false">
        <int-redis:request-handler-advice-chain>
            <beans:bean class="org.springframework.integration.handler.advice.ExpressionEvaluatingRequestHandlerAdvice">
                <beans:property name="onSuccessExpression" value="#redisTemplate.boundListOps(${collection}).trim(-1,1)"/>
            </beans:bean>
        </int-redis:request-handler-advice-chain>
    </int-redis:store-outbound-channel-adapter>

    <bean id="redisConnectionFactory"
          class="org.springframework.data.redis.connection.jedis.JedisConnectionFactory">
        <property name="hostName" value="${host}" />
        <property name="port" value="${port}" />
    </bean>

</beans>
```

So with a stream that uses a JMS based source
```bash
stream create car_echo_jms_to_redis --definition "jms --destination=car | redis-store-capped" --deploy
```
And a controlled jms sender to the car queue this unfortunately this does not provide the desired effect and the collection (after 21 lpush messages) not only has the effect of reversing the order of insert but removing every other item.
```bash 
127.0.0.1:6379> lrange car_echo_jms_to_redis 0 -1
 1) "0000000001"
 2) "0000000003"
 3) "0000000005"
 4) "0000000007"
 5) "0000000009"
 6) "0000000011"
 7) "0000000013"
 8) "0000000015"
 9) "0000000017"
10) "0000000019"
11) "0000000021"

```


##Assertions and Next Steps?
- I do understand how to configure an ExpressionEvaluationRequestHandlerAdvice to run the trim command on the RedisTemplate after the default "lpush" is done - and that module is operational. 
- I do not think the suggested trim(1, -1) has the desired effect but now that you can see what I am trying to do maybe you can suggest something different - and I am unclear really how the trim() operation works based on this - so if you think there is a way to modify that for the desired effect then I could appreciate your help because trim(0,5) doesn't work right either (for instance to define a cap of 50 elements where the oldest is removed after the size of the collection grows beyond 50)
	- If there is no appropriate trim() parameter to give the desired behavior, then I believe what I really need to do is to turn the ```lpush``` to a ```rpush```. I do understand there is a boolean property on the RedisQueueOutboundChannelAdapter to change the default behavior from 'lpush' to an 'rpush'. I **do not** understand how to set that property on the RedisQueueOutboundChannelAdapter itself given the way I have attempted to solve this problem by modifying the sample redis-sink module
		- When I look at Spring Sources I see the only construction of RedisQueueOutboundChannelAdapter done in the RedisMessageBus class - I have no idea what that class does or how to provide it with the propery leftPush = false 
- Any help would be much appreciated. 
- I am a big supporter of your efforts and have tried not to ask lazy or stupid questions and avoid brute-force ways to achieve this and I am many hours into this and want to solve it rather than drop it - Thanks!