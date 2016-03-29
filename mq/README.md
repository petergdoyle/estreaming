#Building the Docker Image for IBM MQ 
##Requirements
- a running Docker Machine
- ```git``` installed 
- ```maven``` installed

##Setup
Everything is pretty much in the provided shell scripts for ease of use. They have been named appropriately for each activity. Feel free to open them up and see how they work. 

#####Create a customizeable Docker image based on the IBM mq-docker git project (https://github.com/ibm-messaging/mq-docker.git)
The ```docker_build``` script will check out the require git repository and build a base ```ibm/mq8``` image, then build a new local image labeled ```estreaming/mq8``` namespace that will be customized and used going forward. Change the name of the image if you wish.  
```bash
[vagrant@estreaming mq]$ ./docker_build.sh 
Cloning into 'mq-docker'...
remote: Counting objects: 133, done.
remote: Total 133 (delta 0), reused 0 (delta 0), pack-reused 133
Receiving objects: 100% (133/133), 42.13 KiB | 0 bytes/s, done.
Resolving deltas: 100% (54/54), done.
Sending build context to Docker daemon 11.26 kB
Step 0 : FROM ubuntu:14.04
Trying to pull repository docker.io/library/ubuntu ... 14.04: Pulling from library/ubuntu
808ef855e5b6: Pull complete 
267903aa9bd1: Pull complete 
d28d8a6a946d: Pull complete 
ab035c88d533: Pull complete 
Digest: sha256:1c8b813b6b6656e9a654bdf29a7decfcc73b92a62b934adc4253b0dc2be9d0a2
Status: Downloaded newer image for docker.io/ubuntu:14.04

 ---> ab035c88d533
Step 1 : MAINTAINER Arthur Barr <arthur.barr@uk.ibm.com>
 ---> Running in b0cc466c43d3
 ---> 3acc0f6b3f39
Removing intermediate container b0cc466c43d3
Step 2 : RUN export DEBIAN_FRONTEND=noninteractive   && MQ_URL=http://public.dhe.ibm.com/ibmdl/export/pub/software/websphere/messaging/mqadv/mqadv_dev80_linux_x86-64.tar.gz   && MQ_PACKAGES="MQSeriesRuntime-*.rpm MQSeriesServer-*.rpm MQSeriesMsg*.rpm MQSeriesJava*.rpm MQSeriesJRE*.rpm MQSeriesGSKit*.rpm"   && echo "mq:8.0" > /etc/debian_chroot   && apt-get update -y   && apt-get install -y     bash     bc     curl     rpm     tar   && mkdir -p /tmp/mq   && cd /tmp/mq   && curl -LO $MQ_URL   && tar -zxvf ./*.tar.gz   && groupadd --gid 1000 mqm   && useradd --uid 1000 --gid mqm --home-dir /var/mqm mqm   && usermod -G mqm root   && cd /tmp/mq/MQServer   && ./mqlicense.sh -text_only -accept   && rpm -ivh --force-debian $MQ_PACKAGES   && /opt/mqm/bin/setmqinst -p /opt/mqm -i   && rm -rf /tmp/mq
 ---> Running in aba3dab8ac80
Ign http://archive.ubuntu.com trusty InRelease
...
...
120 of 120 tasks have been completed successfully.
'Installation1' (/opt/mqm) set as the primary installation.
 ---> 241e78aaf544
Removing intermediate container aba3dab8ac80
Step 3 : COPY *.sh /usr/local/bin/
 ---> cf3c28b12e08
Removing intermediate container 67ff5b28acfc
Step 4 : COPY *.mqsc /etc/mqm/
 ---> b7c323d90ccb
Removing intermediate container 231c326c5514
Step 5 : ENV MQ_QMGR_CMDLEVEL 802
 ---> Running in 181b7be5107f
 ---> 6a7787ad29c9
Removing intermediate container 181b7be5107f
Step 6 : RUN chmod +x /usr/local/bin/*.sh
 ---> Running in 4209d48cc598
 ---> f86b62fbae6f
Removing intermediate container 4209d48cc598
Step 7 : EXPOSE 1414
 ---> Running in f50b08905441
 ---> a6dc2f00f9a4
Removing intermediate container f50b08905441
Step 8 : VOLUME /var/mqm
 ---> Running in 10d3c5b3f3cf
 ---> 89099f53c25e
Removing intermediate container 10d3c5b3f3cf
Step 9 : ENTRYPOINT mq.sh
 ---> Running in a4eefde6925b
 ---> 65e4997fb44c
Removing intermediate container a4eefde6925b
Successfully built 65e4997fb44c
Sending build context to Docker daemon 175.6 kB
Step 0 : FROM ibm/mq8
 ---> 65e4997fb44c
Step 1 : RUN sysctl fs.file-max=524288
 ---> Running in 1f072c3bfd93
sysctl: setting key "fs.file-max": Read-only file system
 ---> edcf46023eb7
Removing intermediate container 1f072c3bfd93
Step 2 : COPY conf/* /etc/mqm/
 ---> ab9d15ece630
Removing intermediate container f5249598c6b4
Successfully built ab9d15ece630
```
And now you should see all three: the base ```docker.io/ubuntu``` image that was used to build the ```ibm/mq``` image and then the ```estreaming/mq8``` image on top of that:
```bash
[vagrant@estreaming mq]$ docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
estreaming/mq8      latest              ab9d15ece630        42 minutes ago      843.4 MB
ibm/mq8             latest              65e4997fb44c        42 minutes ago      843.4 MB
docker.io/ubuntu    14.04               ab035c88d533        11 days ago         187.9 MB
```

#Running the Docker Container 

#####Run the container
You will be propmted for a couple of parameters. The defaults are fine for this exercise but remember them if you change them as you will need tham in later steps. 
```bash
[vagrant@estreaming mq]$ ./docker_run.sh 
fs.file-max = 524288
Enter the Queue Manager name: QM1
Enter the port number: 1414
9b4705ac01d1ecb5cc5ce6fa75050da68751528dc05b29472d3e8cf983b5f412
```

To verify the container started okay, run the ```docker logs``` command on the conatiner and you should see something like the following:
```bash
[vagrant@estreaming mq]$ docker logs estreaming_ibm_mq8_broker 
----------------------------------------
Name:        WebSphere MQ
Version:     8.0.0.4
Level:       p800-004-151022.DE
BuildType:   IKAP - (Production)
Platform:    WebSphere MQ for Linux (x86-64 platform)
Mode:        64-bit
O/S:         Linux 3.10.0-327.10.1.el7.x86_64
InstName:    Installation1
InstDesc:    
Primary:     Yes
InstPath:    /opt/mqm
DataPath:    /var/mqm
MaxCmdLevel: 802
LicenseType: Developer
----------------------------------------
mqconfig: V3.7 analyzing Ubuntu 14.04.4 LTS settings for WebSphere MQ V8.0

System V Semaphores
  semmsl     (sem:1)  250 semaphores                     IBM>=32           PASS
  semmns     (sem:2)  0 of 32000 semaphores      (0%)    IBM>=4096         PASS
  semopm     (sem:3)  32 operations                      IBM>=32           PASS
  semmni     (sem:4)  0 of 128 sets              (0%)    IBM>=128          PASS

System V Shared Memory
  shmmax              18446744073692774399 bytes         IBM>=268435456    PASS
  shmmni              0 of 4096 sets             (0%)    IBM>=4096         PASS
  shmall              0 of 18446744073692774399 pages (0%)    IBM>=2097152      PASS

System Settings
  file-max            1152 of 524288 files       (0%)    IBM>=524288       PASS

Current User Limits (root)
  nofile       (-Hn)  1048576 files                      IBM>=10240        PASS
  nofile       (-Sn)  1048576 files                      IBM>=10240        PASS
----------------------------------------
Checking filesystem...
The tests on the directory completed successfully.
----------------------------------------
WebSphere MQ queue manager created.
Directory '/var/mqm/qmgrs/QM1' created.
The queue manager is associated with installation 'Installation1'.
Creating or replacing default objects for queue manager 'QM1'.
Default objects statistics : 79 created. 0 replaced. 0 failed.
Completing setup.
Setup completed.
WebSphere MQ queue manager 'QM1' starting.
The queue manager is associated with installation 'Installation1'.
5 log records accessed on queue manager 'QM1' during the log replay phase.
Log replay for queue manager 'QM1' complete.
Transaction manager state recovered for queue manager 'QM1'.
Migrating objects for queue manager 'QM1'.
Default objects statistics : 3 created. 0 replaced. 0 failed.
New functions up to command level 802 enabled.
----------------------------------------
WebSphere MQ queue manager 'QM1' starting.
The queue manager is associated with installation 'Installation1'.
5 log records accessed on queue manager 'QM1' during the log replay phase.
Log replay for queue manager 'QM1' complete.
Transaction manager state recovered for queue manager 'QM1'.
WebSphere MQ queue manager 'QM1' started using V8.0.0.4.
----------------------------------------
5724-H72 (C) Copyright IBM Corp. 1994, 2015.
Starting MQSC for queue manager QM1.


       : * © Copyright IBM Corporation 2015, 2016
       : *
       : *
       : * Licensed under the Apache License, Version 2.0 (the "License");
       : * you may not use this file except in compliance with the License.
       : * You may obtain a copy of the License at
       : *
       : * http://www.apache.org/licenses/LICENSE-2.0
       : *
       : * Unless required by applicable law or agreed to in writing, software
       : * distributed under the License is distributed on an "AS IS" BASIS,
       : * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       : * See the License for the specific language governing permissions and
       : * limitations under the License.
       : 
       : * Enable and start a TCP/IP listener on port 1414
     1 : ALTER LISTENER('SYSTEM.DEFAULT.LISTENER.TCP') TRPTYPE(TCP) PORT(1414) CONTROL(QMGR)
AMQ8623: WebSphere MQ listener changed.
     2 : START LISTENER('SYSTEM.DEFAULT.LISTENER.TCP')
AMQ8021: Request to start WebSphere MQ listener accepted.
2 MQSC commands read.
No commands have a syntax error.
All valid MQSC commands were processed.
5724-H72 (C) Copyright IBM Corp. 1994, 2015.
Starting MQSC for queue manager QM1.


       : 
     1 : ALTER QMGR CHLAUTH(DISABLED) CONNAUTH(' ')
AMQ8005: WebSphere MQ queue manager changed.
       : 
     2 : REFRESH SECURITY TYPE(CONNAUTH)
AMQ8560: WebSphere MQ security cache refreshed.
2 MQSC commands read.
No commands have a syntax error.
All valid MQSC commands were processed.
----------------------------------------
QMNAME(QM1)                                               STATUS(Running)
```

####Turn off Authentication (Recommended initially)
By default, MQ authorizations are required to connect to the Queue Manager. You should see the commands that turn auth off executed when the container starts up - see above. The documentation for the github mq-docker image specifes that specifying security settings in a file that is COPYied to /etc/mqm/config.mqsc will apply security to the Queue Manager but this seems to not be complete. Verify auth settings by running the ```docker_exec_check_auth``` script or run the docker command directly ```docker exec -ti estreaming_ibm_mq8_broker /bin/sh -c 'echo "DIS QMGR" |runmqsc QM1'  |egrep 'CHLAUTH|CONNAUTH'``` to run the  ```runmqsc```  on the container and invoking a ```DIS QMGR``` and look for the ```CONNAUTH( )```entry and ```CHLAUTH``` - if there is some value in between the parenthesis then auth security in some form is still on. 

```bash
[vagrant@estreaming mq]$ docker exec -ti estreaming_ibm_mq8_broker /bin/sh -c 'echo "DIS QMGR" |runmqsc QM1'  |egrep 'CHLAUTH|CONNAUTH'
   CHLEV(DISABLED)                         CHLAUTH(DISABLED)
   CONFIGEV(DISABLED)                      CONNAUTH( )

```
When the container is built it copies a ```config.mqsc``` file into ```/etc/mqm/config.mqsc``` on the machine image and should be picked up by default. The contents in the ```config.mqsc``` should look like this by default:
```
ALTER QMGR CHLAUTH(DISABLED) CONNAUTH(' ')
REFRESH SECURITY TYPE(CONNAUTH)
```

####Turn on Authentication (Recommended later when things are running and security requirements are understood)
So if you want the authorization/authentication to be maintained on privileged users then as specified in conf/config_clauth_on.mqsc then run this script. 

```bash
[vagrant@estreaming mq]$ ./docker_exec_apply_auth.sh
```

It will prompt you for a user name and password that will get created on the server as an mq privileged user (added to the mqm group). Then it will run the following mqsc commands:
```
DEFINE CHANNEL(PASSWORD.SVRCONN) CHLTYPE(SVRCONN)
SET CHLAUTH(PASSWORD.SVRCONN) TYPE(BLOCKUSER) USERLIST('nobody') DESCR('Allow privileged users on this channel')
SET CHLAUTH('*') TYPE(ADDRESSMAP) ADDRESS('*') USERSRC(NOACCESS) DESCR('BackStop rule')
SET CHLAUTH(PASSWORD.SVRCONN) TYPE(ADDRESSMAP) ADDRESS('*') USERSRC(CHANNEL) CHCKCLNT(REQUIRED)
ALTER AUTHINFO(SYSTEM.DEFAULT.AUTHINFO.IDPWOS) AUTHTYPE(IDPWOS) ADOPTCTX(YES)
REFRESH SECURITY TYPE(CONNAUTH)

```
If you wish to add other uses, then you need to add other privileged users but the above mqsc directives should allow authentication by privileged users into the Queue Manager. For more information consult the reference below.


#####Destroy the container (not now! but for future reference)
```bash
[vagrant@estreaming mq]$ ./docker_destroy.sh
```

#Running the Client Code to put a Message on the Queue

Once the container is up and running and appears to be configured properly, we can test the installation using a simple client JMS program. This program is located in the mq-jms-client directory and is named ```MQJMSMessageSender```. You can find it and look at it using any text editor. There is a script that prompts you for some details about how to connect to the Queue Manager and then runs the program. The script to test is ```test_mq_installation```
```bash
[vagrant@estreaming mq]$ cd mq-jms-client
[vagrant@estreaming mq-jms-client]$ tree
.
├── clean_build.sh
├── lib
├── mvn_install_mq_client_jars.sh
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── cleverfishsoftware
│   │   │           └── jms
│   │   │               └── mq
│   │   │                   └── MQJMSMessageSender.java
│   │   └── resources
│   └── test
│       └── java
├── test_mq_installation.sh
```

####Install the IBM MQ V8 JMS client jars 
IBM does not provide any open sourced or public maven accessible jars to connect to MQ. They do happen to be available in the Docker server image itself so you can obtain them from there. First run the script in the ```mq``` folder called ```docker_exec_cp_mq_client_jars``` to copy them from the running container to the ```mq/lib``` folder, then run the ```mq/mq-jms-client/mvn_install_mq_client_jars.sh``` to install the to the local maven repository. You should see each one install. There are about 8 jars to install this way.

```bash
[vagrant@estreaming mq-jms-client]$ ../docker_exec_cp_mq_client_jars.sh
[vagrant@estreaming mq-jms-client]$ ./mvn_install_mq_client_jars.sh
...
[INFO] --- maven-install-plugin:2.4:install-file (default-cli) @ mq-jms-client ---
[INFO] Installing /vagrant/mq/lib/com.ibm.mq.jms.Nojndi.jar to /home/vagrant/.m2/repository/com/ibm/com.ibm.mq.jms.Nojndi/8.0.0.0/com.ibm.mq.jms.Nojndi-8.0.0.0.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.367 s
[INFO] Finished at: 2016-03-29T11:12:31-04:00
[INFO] Final Memory: 8M/102M
[INFO] ------------------------------------------------------------------------
```

####Create a Queue

MQ requires that a Queue be defined on the server first so first thing is to create that Queue. Run the ```docker_exec_create_queue``` script from the parent directory (if you are in mq/mq-jms-client directory) to upen up a ```runmqsc``` session on the container and run the ```DEFINE QL(<queue-name>)``` where `<queue-name>` is the name of the Queue ("QUEUE1"  is the default name in the client code but if you change it, remember it, because you will be prompted for that later when you run the program). You will then compile the program and be prompted for details about the installation by running the ```test_mq_installation``` script. After it runs, you should see a success message at the end

```bash
[vagrant@estreaming mq-jms-client]$ ../docker_exec_create_queue.sh 
Enter the Queue Manager name: QM1
Enter the Queue name: QUEUE1
5724-H72 (C) Copyright IBM Corp. 1994, 2015.
Starting MQSC for queue manager QM1.


     1 : DEFINE QL(QUEUE1)
AMQ8006: WebSphere MQ queue created.
One MQSC command read.
No commands have a syntax error.
All valid MQSC commands were processed.

```

####Compile and Run the Client program
```bash
[vagrant@estreaming mq-jms-client]$ ./test_mq_installation.sh
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
- [Introducing a Docker image for MQ Advanced for Developers](https://www.ibm.com/developerworks/community/blogs/messaging/entry/introducing_a_docker_image_for_mq_advanced_for_developers?lang=en) Arthur Barr  | Feb 24 2015 
- [Getting going without turning off MQ Security](https://www.ibm.com/developerworks/community/blogs/messaging/entry/getting_going_without_turning_off_mq_security?lang=en) Morag Hughson  | Feb 12 2015 
- [Run WebSphere® MQ commands on a queue manager](https://www.ibm.com/support/knowledgecenter/#!/SSFKSJ_7.5.0/com.ibm.mq.ref.adm.doc/q083460_.htm)
