Welcome To estreaming!
======================

The goal of this project to build a server and a client capable producing and consuming a stream of data from any data source.

The following instructions assume you have a running estreaming virtualbox running and you can connect to it using ssh or x2go client. It is easier to use x2go rather than using an ssh terminal client as we will be opening up quite a few terminals. Follow the instructions [here](https://github.com/petergdoyle/estreaming) or [file a git issue](https://github.com/petergdoyle/estreaming/issues) if you are having problems, and I will try to help you.

Spring XD provides us with a very straightforward, and powerful runtime system to define and manage data pipelines used for streaming data through. After a few examples you will see how it allows us to create streaming data pipeline definitions that are very much like creating UNIX pipelines. If you are not a UNIX user and are not familiar with the the pipeline concept, have a look [here](http://www.westwind.com/reference/os-x/commandline/pipes.html) first. A Spring XD "stream definition" will at a minimum consist of a data source and a data sink. Of course we can create more complex streams by adding other pipes and filters along the way.


**So let's get going**. If you have your virtualbox estreaming machine up and running and have connected to it using the X2Go client, open up a new terminal and type:

```[vagrant@estreaming ~]$ sudo xd-singlenode```

*This will start a single node instance of Spring XD and that is fine for this demo. For details on setting up Spring XD in more advanced configurations, check [here](http://docs.spring.io/spring-xd/docs/current/reference/html/#xd-distributed-runtime).*

You should see Spring XD start up

![spring-xd-singlenode-startup](spring-xd-singlenode-startup.png)

Give it a minute or so to get fully enabled and you should see something like:

```2015-07-14 16:53:47,743 1.1.2.RELEASE  INFO DeploymentSupervisor-0 server.ContainerListener - Scheduling deployments to new container(s) in 15000 ms```

Now open another tab on that terminal and type:

```[vagrant@estreaming ~]$ xd-shell```

You should see the Spring XD shell (client) start up

![spring-xd-shell](spring-xd-shell.png)
