

### Build a Real-time Data Streaming Solution (estreaming) 

estreaming uses a number of open source projects stacked together:

* [Spring XD](http://projects.spring.io/spring-xd/) - Spring XD is a unified, distributed, and extensible system for data ingestion, real time analytics, batch processing, and data export.It is rich with features to create powerful data streams using a unix pipeline metaphor [reference](http://docs.spring.io/spring-xd/docs/1.2.0.RELEASE/reference/html/). It builds on top of mature, powerful and venerable Spring projects (Spring Data, Spring IO, Spring Data, Spring Batch, Spring Security, and Spring Boot)
* [node.js] - Node.js is a platform built on Chrome's JavaScript runtime for easily building fast, scalable network applications. Node.js uses an event-driven, non-blocking I/O model that makes it lightweight and efficient, perfect for data-intensive real-time applications that run across distributed devices.
* [Express] - Express is a minimal and flexible Node.js web application framework that provides a robust set of features for web and mobile applications. With a myriad of HTTP utility methods and middleware at your disposal, creating a robust API is quick and easy.
* [Gulp] - Gulp is a streaming build system, by using node’s streams file manipulation is all done in memory, and a file isn’t written until you tell it to do so.
* [MongoDb](https://www.mongodb.org/) - MongoDB is a cross-platform document-oriented database. Classified as a NoSQL database, MongoDB eschews the traditional table-based relational database structure in favor of JSON-like documents. Two very special features of Mongo are used for Streaming: Tailable Cursors and Capped Collections. A Mongo tailable cursor gets its inspiration from the tail -f command in unix systems. For those unfamiliar with what that does, the idea is to open a file, listen for new additions to the end of the file, and print those. The program does not terminate when it reaches the end of the file, instead, it waits for more data. Using this approach, a simple message queue can be created with MongoDB. A Mongo capped collection is a fixed size and only allows insertions. This means that once the number of documents exhausts the collection size, newly written documents will start overwriting the first inserted documents. An important note about capped collections is that insertion order is the natural sort order. This means that when the tail cursor fetches documents, it will get them back in the order they were inserted. 
* [Apache Kafka](http://kafka.apache.org/) Apache Kafka is publish-subscribe messaging rethought as a distributed commit log.Kafka will be used to build the durable message pipelines between sources and sinks. 
* [Apache Storm](https://storm.apache.org/)  Apache Storm is a free and open source distributed realtime computation system. Storm makes it easy to reliably process unbounded streams of data, doing for realtime processing what Hadoop did for batch processing. Storm is simple, can be used with any programming language, and is a lot of fun too! Storm can be used to build aggregate data streams where a consumer may not be able to digest the entire firehose stream because of it's volume and velocity. It can be used for the same purpose to feed Hadoop by dramatically reducing the size of the dataset to be stored in HDFS. Storm has many use cases: realtime analytics, online machine learning, continuous computation, distributed RPC, ETL, and more. Storm is fast: a benchmark clocked it at over a million tuples processed per second per node. It is scalable, fault-tolerant, guarantees your data will be processed, and is easy to set up and operate. 

### Installation
**Install Pre-requisites:**

1. [Virtualbox](https://www.virtualbox.org/wiki/Downloads)
2. [Vagrant](https://www.vagrantup.com/downloads.html)
3. Git ([Linux](), [Windows](https://msysgit.github.io/))
5. [X2Go Client](http://wiki.x2go.org/doku.php/download:start)


## Usage

**Create a VirtualBox VM**

Whereever you cloned your git repo, move to that directory ($ESTREAMING_REPO_HOME) and then get into the following location:

```sh
$ cd $ESTREAMING_REPO_HOME/vagrant/estreaming
```

You should see a file there called Vagrantfile. That has the details of how the new virtualbox box will be provisioned. **Please note** that the default machine spec is set to 2 vcpus and 2048 Mb memory allocation. This is the minimum acceptable vitural machine spec require to run the demo, so that may or may not work depending on the capabilities of the hardware of the host machine.

Pull a "base" box with this command. 
```sh
$ vagrant box add https://github.com/holms/vagrant-centos7-box/releases/download/7.1.1503.001/CentOS-7.1.1503-x86_64-netboot.box -- name  CentOS-7.1.1503-x86_64
```
Install the CentOS 7 fix
```sh
$ vagrant plugin install vagrant-centos7_fix
```
Once you have the Pre-reqs installed find a folder on your local drive and clone the estreaming repo. For those on Windows, I will only include the instructions in bash (linux), so please install Git Bash  and then we will all be talking bash. I have idea how/if this works on Cygwin. 

```sh
$ git clone [https://github.com/petergdoyle/estreaming.git] estreaming
```
Now move into the folder wher the Vagrantfile is located
```sh
$ cd estreaming/vagrant
```

Start the box and you should see a bunch of update and intallation messages on the console. This may take a few minutes but run the command:

```sh
$ vagrant up
```

To suspend your box, use this command:

```sh
$ vagrant suspend
```

To remove the box altogether (maybe if problems installing or provisioning):

```sh
$ vagrant destroy
```

If everything went well after running ```vagrant up``` then you should be able to connect to the running box using ```vagrant ssh``` (again you have to be in the $ESTREAMING_REPO_HOME/vagrant/estreaming directory)


**Connect to the running box**

To connect from any ssh client:
Locate the private key for that box (linux, for windows refer to http://stackoverflow.com/questions/9885108/ssh-to-vagrant-box-in-windows)

```sh
$ vagrant ssh-config | grep IdentityFile  | awk '{print $2}'
```


Now you should have a running CentOS 7 Linux system with all the OS and estreaming components installed for you. Now you will be connect to the running system using X2Go.Once you have the location of the rsa private_key identified (mine happened to be $HOME/vagrant/estreaming/.vagrant/machines/default/virtualbox/private_key), connect to the the running virtual machine using X2Go. Add a new connection using the name "vagrant"and NO password but you must specify the location of the rsa private_key file you found with the previous command. As well under the window manager option, specify XFCE as the desktop manager. It will not work without these options. 


As well, it is possible to use a regular ssh command to connect from the command line (on the host machine but we are going to open up a lot of terminals so that is not recommended)

```sh
$ ssh -XC -c blowfish-cbc,arcfour -i $HOME/vagrant/estreaming/.vagrant/machines/default/virtualbox/private_key -l vagrant -p 2222 127.0.0.1
```


Now run the [demo](DEMO.md)



