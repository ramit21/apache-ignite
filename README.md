# apache-ignite
## Apache ignite poc

**Getting started**: download apache ingnite and setup the environment.
```
https://dzone.com/articles/getting-started-with-apache-ignite
```

**About Poc**:

Note the dependencies in the pom for ignite.

IgniteNodeStart.java: Run it to launch a node. Run it multiple times to launch more nodes, 
and see the nodes auto discover each other.

**SQL**

Look at Organization and Person classes in sql package, on how to create indexes on selective fields.

Start couple of nodes from IgniteNodeStart, then execute the other client examples. 

After executing CacheQueryExample, with data already present in the running nodes, execute the CacheAggegrationExample.java

**API**

After starting the server nodes, execute CacheApiExample.java for Async processing, and XXXX.java for using the transaction API.

**Compute Grid**

ComputeGridExample.java shows how to broadcast to all the nodes, or to only the remote nodes.

DistributedComputeExample.java shows how using Java 8 Lambda, we can split the task between various nodes.


## Ignite Server mode vs client mode

If the Ignite is used as an embedded server in a java application, they the Ignite should be in server mode, that is, 
Ignite should be started with

```
Ignite ignite = Ignition.start(configFile)

```
If you have setup an Ignite cluster that are running as standalone processes, then in the java code, start Ignite in client mode
, so that the client mode Ignite can connect to the Ignite cluster, and CRUD the cache data that resides in the ignite cluster.

```
Ignition.setClientMode(true);
Ignite ignite = Ignition.start(configFile) 
```

## Features of Ignite

https://ignite.apache.org/features.html

