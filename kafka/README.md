###Use Case 1

####Pub-Sub

#####Consumer 1 (group 1)
```bash
Enter the topic: use-case-1
Enter the zk host/port: localhost:2181
Enter the offset: --from-beginning
Enter the consumer group id: consumer-group-1
Run new kafka consumer: y
Enter the bootstrap server: localhost:9092
0000000001
0000000002
0000000003
0000000004
0000000005
0000000006
0000000007
0000000008
0000000009
0000000010
0000000011
0000000012
0000000013
0000000014
0000000015
0000000016
0000000017
0000000018
0000000019
0000000020
^CProcessed a total of 20 messages
```

#####Consumer 2 (group 1)
```bash
Enter the topic: use-case-1
Enter the zk host/port: localhost:2181
Enter the offset: --from-beginning
Enter the consumer group id: consumer-group-2
Run new kafka consumer: y
Enter the bootstrap server: localhost:9092
0000000001
0000000002
0000000003
0000000004
0000000005
0000000006
0000000007
0000000008
0000000009
0000000010
0000000011
0000000012
0000000013
0000000014
0000000015
0000000016
0000000017
0000000018
0000000019
0000000020
^CProcessed a total of 20 messages
```
