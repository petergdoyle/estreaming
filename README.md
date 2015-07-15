

### Build a Real-time Data Streaming Solution (estreaming)
==========================================================

This project uses a number of open source projects stacked together:

* [Spring XD](http://projects.spring.io/spring-xd/) - Spring XD is a unified, distributed, and extensible system for data ingestion, real time analytics, batch processing, and data export.It is rich with features to create powerful data streams using a unix pipeline metaphor [reference](http://docs.spring.io/spring-xd/docs/1.2.0.RELEASE/reference/html/). It builds on top of mature, powerful and venerable Spring projects (Spring Data, Spring IO, Spring Data, Spring Batch, Spring Security, and Spring Boot)
* [node.js](https://nodejs.org/) - Node.js is a platform built on Chrome's JavaScript runtime for easily building fast, scalable network applications. Node.js uses an event-driven, non-blocking I/O model that makes it lightweight and efficient, perfect for data-intensive real-time applications that run across distributed devices.
* [Express](http://expressjs.com/) - Express is a minimal and flexible Node.js web application framework that provides a robust set of features for web and mobile applications. With a myriad of HTTP utility methods and middleware at your disposal, creating a robust API is quick and easy.
* [Gulp](http://gulpjs.com/) - Gulp is a streaming build system, by using node’s streams file manipulation is all done in memory, and a file isn’t written until you tell it to do so.
* [MongoDb](https://www.mongodb.org/) - MongoDB is a cross-platform document-oriented database. Classified as a NoSQL database, MongoDB eschews the traditional table-based relational database structure in favor of JSON-like documents. We will leverage two very special features of Mongo are used for Streaming: Tailable Cursors and Capped Collections. A Mongo tailable cursor gets its inspiration from the tail -f command in unix systems. For those unfamiliar with what that does, the idea is to open a file, listen for new additions to the end of the file, and print those. The program does not terminate when it reaches the end of the file, instead, it waits for more data. Using this approach, a simple message queue can be created with MongoDB. A Mongo capped collection is a fixed size and only allows insertions. This means that once the number of documents exhausts the collection size, newly written documents will start overwriting the first inserted documents. An important note about capped collections is that insertion order is the natural sort order. This means that when the tail cursor fetches documents, it will get them back in the order they were inserted.
* [Apache Kafka](http://kafka.apache.org/) Apache Kafka is publish-subscribe messaging rethought as a distributed commit log.Kafka will be used to build the durable message pipelines between sources and sinks.
* [Apache Storm](https://storm.apache.org/)  Apache Storm is a free and open source distributed realtime computation system. Storm makes it easy to reliably process unbounded streams of data, doing for realtime processing what Hadoop did for batch processing. Storm is simple, can be used with any programming language, and is a lot of fun too! Storm can be used to build aggregate data streams where a consumer may not be able to digest the entire firehose stream because of it's volume and velocity. It can be used for the same purpose to feed Hadoop by dramatically reducing the size of the dataset to be stored in HDFS. Storm has many use cases: realtime analytics, online machine learning, continuous computation, distributed RPC, ETL, and more. Storm is fast: a benchmark clocked it at over a million tuples processed per second per node. It is scalable, fault-tolerant, guarantees your data will be processed, and is easy to set up and operate.


Things are divided up:
1. Set up the virtual machine using VirtualBox and Vagrant [Installation](INSTALLATION.md)
2. Up and Running the [Streaming API Server](STREAMING_API_SERVER.md) using Spring XD, Mongo, and Node.js
2. Up and Running the [Streaming API Client](STREAMING_API_CLIENT.md) using Node.js
3. Visualization of the the Stream using [Spring XD Analytics](ANALYTICS.md)
3. Building an Aggregate Stream using [Storm and Kafka](STORM_AND_KAFKA.md)
