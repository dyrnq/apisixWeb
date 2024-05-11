#!/usr/bin/env bash

curl http://127.0.0.1:9180/apisix/admin/routes/50000012 \
-H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
  "uri": "/headers",
  "plugins": {
    "traffic-split": {
      "rules": [
        {
          "match": [
              {
                "vars": [ ["http_release","==","new_release"] ]
              }
          ],
          "weighted_upstreams": [
            {
              "upstream": {
                "name": "upstream_A",
                "type": "roundrobin",
                "nodes": {
                  "127.0.0.1:9992":10
                },
                "timeout": {
                  "connect": 1,
                  "send": 1,
                  "read": 1
                }
              },
              "weight": 1
            },
            {
              "weight": 1
            }
          ]
        }
      ]
    }
  },
  "upstream": {
      "type": "roundrobin",
      "nodes": {
        "127.0.0.1:9991": 1
      }
  }
}
'

curl -X GET -i --header 'release: new_release' http://127.0.0.1:9080/headers;
curl -X GET -i --header 'release: old_release' http://127.0.0.1:9080/headers;