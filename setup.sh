#!/usr/bin/env bash

docker exec -i -t reactivemongoissue_rs0-00 /usr/bin/mongo --port 27027 --eval 'rs.initiate({_id : "rs0", members: [ { _id : 0, host : "toxiproxy:27017" } ]})';

sleep 5;

docker exec -i -t reactivemongoissue_rs0-00 /usr/bin/mongo admin --port 27027 --eval 'db.createUser({ "user": "root", "pwd": "secret", "roles": [ "root" ] });';
docker exec -i -t reactivemongoissue_rs0-00 /usr/bin/mongo admin --port 27027 --username root --password secret --eval 'db.createUser({ "user": "eqs", "pwd": "secret", "roles": [ { "role": "dbAdmin", "db": "eqs" }, { "role": "readWrite", "db": "eqs" } ] });';

docker exec -i -t reactivemongoissue_rs0-00 /usr/bin/mongo admin --port 27027 --username root --password secret --eval 'rs.add("toxiproxy:27018")';
docker exec -i -t reactivemongoissue_rs0-00 /usr/bin/mongo admin --port 27027 --username root --password secret --eval 'rs.add("toxiproxy:27019")';

#var eqs = {
#  "user": "eqs",
#  "pwd": "secret",
#  "roles": [

#  ]
#};
#use admin;
#db.createUser(eqs);