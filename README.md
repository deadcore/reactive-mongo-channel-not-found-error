# Reactive mongo bug - Channel not found

Proof of concept of a suspected bug in reactive Mongo.

## Why?

The issue is very hard to reproduce but when it strikes the primary is unable to be connected to. 
This was experienced several time on our production systems using a hosted instance of Mongo through [Atlas](https://www.mongodb.com/cloud/atlas)

## How?

This proof of concept will setup a project similar to a Play2.6 application.

It will then attempt to save several entities and then disable the connection to one of the Mongo instances

Reactive Mongo should re-connect to the primary when one becomes available after an election

Sometimes Reactive Mongo will destory the connections but won't re-authenticate. The source of this is unknown and where
I believe the bug to lie.

## Getting Started

1. Run `./start.sh`
2. Wait for the mongo instances to start
3. Run `./setup.sh`
4. Run `Bootstrap.scala`

### Expected

After several runs a `ChannelNotFound` exception will get thrown but inside the message will me `'localhost:27017' { authenticated:0, connected:10, channels:10 } (Supervisor-2/Connection-3)'`
hinting that none of the connections are getting drained and re-authenticated correctly

An example of the console out is:

```
2017-08-25 11:23:42,297  INFO main ApplicationImpl.test - Injecting downstream latency into List(rs0-2)
2017-08-25 11:23:42,297  INFO main ApplicationImpl.$anonfun$test$1 - Disabling mongo instance rs0-2
2017-08-25 11:23:42,300  INFO main ApplicationImpl.$anonfun$countdown$1 - 15 seconds to go
2017-08-25 11:23:42,302  WARN reactivemongo-akka.actor.default-dispatcher-2 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The primary is unavailable, is there a network problem?
2017-08-25 11:23:43,301  INFO main ApplicationImpl.$anonfun$countdown$1 - 14 seconds to go
2017-08-25 11:23:44,301  INFO main ApplicationImpl.$anonfun$countdown$1 - 13 seconds to go
2017-08-25 11:23:45,302  INFO main ApplicationImpl.$anonfun$countdown$1 - 12 seconds to go
2017-08-25 11:23:46,302  INFO main ApplicationImpl.$anonfun$countdown$1 - 11 seconds to go
2017-08-25 11:23:47,303  INFO main ApplicationImpl.$anonfun$countdown$1 - 10 seconds to go
2017-08-25 11:23:48,303  INFO main ApplicationImpl.$anonfun$countdown$1 - 9 seconds to go
2017-08-25 11:23:49,304  INFO main ApplicationImpl.$anonfun$countdown$1 - 8 seconds to go
2017-08-25 11:23:50,304  INFO main ApplicationImpl.$anonfun$countdown$1 - 7 seconds to go
2017-08-25 11:23:50,959  WARN reactivemongo-akka.actor.default-dispatcher-5 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] Node[localhost:27017: Secondary (10/10 available connections), latency=19], auth=Set(Authenticated(admin,eqs)) hasn't answered in time to last ping! Please check its connectivity
2017-08-25 11:23:51,159  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,160  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,160  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,160  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,160  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,161  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,161  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,161  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,161  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,161  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] The node set is authenticated, but the primary is not available: None -> Set(toxiproxy:27017, localhost:27017), Set(toxiproxy:27018, localhost:27018), Set(toxiproxy:27019, localhost:27019), Set(toxiproxy:27017), Set(toxiproxy:27019)
2017-08-25 11:23:51,305  INFO main ApplicationImpl.$anonfun$countdown$1 - 6 seconds to go
2017-08-25 11:23:52,305  INFO main ApplicationImpl.$anonfun$countdown$1 - 5 seconds to go
2017-08-25 11:23:53,305  INFO main ApplicationImpl.$anonfun$countdown$1 - 4 seconds to go
2017-08-25 11:23:54,306  INFO main ApplicationImpl.$anonfun$countdown$1 - 3 seconds to go
2017-08-25 11:23:55,306  INFO main ApplicationImpl.$anonfun$countdown$1 - 2 seconds to go
2017-08-25 11:23:56,307  INFO main ApplicationImpl.$anonfun$countdown$1 - 1 seconds to go
2017-08-25 11:23:57,307  INFO main ApplicationImpl.$anonfun$countdown$1 - 0 seconds to go
2017-08-25 11:23:58,308  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(1BRa1JO3RIk51mHvV36eYZkvZ5prciqS)
2017-08-25 11:24:05,531  INFO main ApplicationImpl.blockingCreateAndSaveUser - Attempted to save user with result: Failure(dao.MongoDao$DbOperationFailedException: DB operation failed: MongoError['No primary node is available! (Supervisor-2/Connection-3)'])
2017-08-25 11:24:05,532  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(6fiCaLKcgGtdnfu9l59wSiUMjC5GwiZK)
2017-08-25 11:24:12,749  INFO main ApplicationImpl.blockingCreateAndSaveUser - Attempted to save user with result: Failure(dao.MongoDao$DbOperationFailedException: DB operation failed: MongoError['No primary node is available! (Supervisor-2/Connection-3)'])
2017-08-25 11:24:12,749  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(XymSB6CqjZbQxumo5aCBQiDLtn7zYsTb)
2017-08-25 11:24:19,969  INFO main ApplicationImpl.blockingCreateAndSaveUser - Attempted to save user with result: Failure(dao.MongoDao$DbOperationFailedException: DB operation failed: MongoError['No primary node is available! (Supervisor-2/Connection-3)'])
2017-08-25 11:24:19,969  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(mcWetK01SAX3xNMxlawZvpDPXmYxhdtH)
2017-08-25 11:24:21,843  WARN reactivemongo-akka.actor.default-dispatcher-4 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] Node[localhost:27019: Unknown (1/10 available connections), latency=9223372036854775807], auth=Set() hasn't answered in time to last ping! Please check its connectivity
2017-08-25 11:24:27,189  INFO main ApplicationImpl.blockingCreateAndSaveUser - Attempted to save user with result: Failure(dao.MongoDao$DbOperationFailedException: DB operation failed: MongoError['No primary node is available! (Supervisor-2/Connection-3)'])
2017-08-25 11:24:27,189  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(OoqDKmvvtZGZYh1S8L8e2wTUAJrAN9yM)
2017-08-25 11:24:34,409  INFO main ApplicationImpl.blockingCreateAndSaveUser - Attempted to save user with result: Failure(dao.MongoDao$DbOperationFailedException: DB operation failed: MongoError['No primary node is available! (Supervisor-2/Connection-3)'])
2017-08-25 11:24:34,409  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(6kBbzkKMzKyCfYVxRkyiEs9sPmgeFhQ8)
2017-08-25 11:24:41,629  INFO main ApplicationImpl.blockingCreateAndSaveUser - Attempted to save user with result: Failure(dao.MongoDao$DbOperationFailedException: DB operation failed: MongoError['No primary node is available! (Supervisor-2/Connection-3)'])
2017-08-25 11:24:41,630  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(L4Q8vax1iI3ymUNiAftP73hdJiMpjUEy)
2017-08-25 11:24:41,843  WARN reactivemongo-akka.actor.default-dispatcher-6 reactivemongo.util.LazyLogger$LazyLogger.warn - [Supervisor-2/Connection-3] Node[localhost:27018: Secondary (10/10 available connections), latency=23], auth=Set(Authenticated(admin,eqs)) hasn't answered in time to last ping! Please check its connectivity
2017-08-25 11:24:48,849  INFO main ApplicationImpl.blockingCreateAndSaveUser - Attempted to save user with result: Failure(dao.MongoDao$DbOperationFailedException: DB operation failed: MongoError['No primary node is available! (Supervisor-2/Connection-3)'])
2017-08-25 11:24:48,849  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(iW0OWcHOLj9r4gu9GuISiZ68zFkAFMBw)
2017-08-25 11:24:56,070  INFO main ApplicationImpl.blockingCreateAndSaveUser - Attempted to save user with result: Failure(dao.MongoDao$DbOperationFailedException: DB operation failed: MongoError['No primary node is available! (Supervisor-2/Connection-3)'])
2017-08-25 11:24:56,070  INFO main ApplicationImpl.clearToxics - Removing all toxics
2017-08-25 11:24:56,085  INFO main ApplicationImpl.clearToxics - Finished clearing down all toxics
2017-08-25 11:24:56,085  INFO main ApplicationImpl.$anonfun$start$2 - Run number 8
2017-08-25 11:24:56,085  INFO main ApplicationImpl.clearToxics - Removing all toxics
2017-08-25 11:24:56,099  INFO main ApplicationImpl.clearToxics - Finished clearing down all toxics
2017-08-25 11:24:56,099  INFO main ApplicationImpl.blockingCreateAndSaveUser - Saving user: User(Eeb7H15nRzk1D7IAtju9MpcbJvpGi8st)
2017-08-25 11:25:09,159  ERROR scala-execution-context-global-44 reactivemongo.util.LazyLogger$LazyLogger.error - [Supervisor-2/Connection-3] Got an error, no more attempts to do. Completing with a failure... 
reactivemongo.core.actors.Exceptions$ChannelNotFound: MongoError['Channel not found from the primary node: 'localhost:27017' { authenticated:0, connected:10, channels:10 } (Supervisor-2/Connection-3)']
```