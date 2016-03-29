#Building the Docker Image for IBM MQ 
##Requirements
- a running Docker Machine
- ```git``` installed 
- ```maven``` installed

##Setup
Everything is pretty much in the provided shell scripts for ease of use. They have been named appropriately for each activity. Feel free to open them up and see how they work. 

#####Create a customizeable Docker image using the mq-docker git project (https://github.com/ibm-messaging/mq-docker.git)
This script will check out the require git repository and build a base ibm/mq8 image, then build a new image from that with the estreaming/ namespace. 
```bash
$./docker_build.sh
```

#Running the Docker Container 

#####Run the container
```bash
$./docker_run.sh
```

####Turn on Authentication 
By default, there is no authentication required to connect to the Queue Manager (see Dockerfile). The documentation for the github mq-docker image specifes that specifying security settings in a file that is COPYied to /etc/mqm/config.mqsc will apply security to the Queue Manager but this seems to not be working. So Security without any customization is applied. So if you want the authorization/authentication to be maintained on privileged users then as specified in conf/config_clauth_on.mqsc then run this script. You can verify settings by running the ```docker_exec_run_runmqsc.sh``` script to upen up a ```runmqsc``` session on the container then running a ```DIS QMGR``` and look for the ```CONNAUTH( )```entry - if there is some value in between the parenthesis then security in some form is still on. 

```bash
$./docker_exec_apply_auth.sh
```

####Turn off Authentication 
If you don't want any authentication/authorization on the Queume Manager then run this script. 

```bash
$./docker_exec_apply_auth.sh
```

#####Destroy the container
```bash
$./docker_destroy.sh
```

#Running the Client Code to put a Message on the Queue
MQ requires that a Queue be defined on the server first so first thing is to create that Queue. Run the ```docker_exec_run_runmqsc.sh``` script to upen up a ```runmqsc``` session on the container. Then run the ```DEFINE QL(<queue-name>)``` where `<queue-name>` is the name of the Queue. You will then compile the program and be prompted for details about the installation by running the ```test_mq_installation``` script. After it runs, you should see a success message at the end
####Compile and Run the Client program
```bash
$ cd mq-jms-client
$ ./test_mq_installation.sh
...
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 2.205 s
[INFO] Finished at: 2016-03-29T10:58:50-04:00
[INFO] Final Memory: 22M/181M
[INFO] ------------------------------------------------------------------------
Enter the host name: localhost
Enter the port number: 1414
Enter the Queue Manager name: QM1
Enter the Channel name: SYSTEM.DEF.SVRCONN
Enter the Queue name: QUEUE1
Enter the Message text: Hello World
Is security enabled on the Queue Manager? (y/n) n
successfully sent 1 message to the queue(QUEUE1)
```



##References
[Introducing a Docker image for MQ Advanced for Developers](https://www.ibm.com/developerworks/community/blogs/messaging/entry/introducing_a_docker_image_for_mq_advanced_for_developers?lang=en) Arthur Barr  | Feb 24 2015 
[Getting going without turning off MQ Security](https://www.ibm.com/developerworks/community/blogs/messaging/entry/getting_going_without_turning_off_mq_security?lang=en) Morag Hughson  | Feb 12 2015 
