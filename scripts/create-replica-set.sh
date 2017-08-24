#!/usr/bin/env bash
set -x

sleep 5

mongo --port 27027 <<EOF
var cfg = {
    "_id": "rs0",
    "version": 1,
    "members": [
        {
            "_id": 0,
            "host": "toxiproxy:27017",
            "priority": 1
        },
        {
            "_id": 1,
            "host": "toxiproxy:27018",
            "priority": 1
        },
        {
            "_id": 2,
            "host": "toxiproxy:27019",
            "priority": 1
        }
    ]
};
rs.initiate(cfg);
EOF

#var eqs = {
#  "user": "eqs",
#  "pwd": "secret",
#  "roles": [
#    {
#      "role": "dbAdmin",
#      "db": "eqs"
#    },
#    {
#      "role": "readWrite",
#      "db": "eqs"
#    }
#  ]
#};
#
#var superuser = {
#  "user": "root",
#  "pwd": "secret",
#  "roles": [ "root" ]
#};
#
#rs.initiate(cfg);
#
#use admin
#db.createUser(eqs);
#db.createUser(superuser);