#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

fun_install_httpbin() {
  docker rm -f go-httpbin &>/dev/null || true ;
  docker run -d --name=go-httpbin --restart always --network host --ulimit nofile=40000:40000 mccutchen/go-httpbin go-httpbin -port 9991
}


fun_install_httpbin_route(){


curl http://127.0.0.1:9180/apisix/admin/routes/9001 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
  "name": "httpbin",
  "plugins": {
    "proxy-rewrite": {
      "use_real_request_uri_unsafe": false,
      "regex_uri": [
        "^/([^/]+)/(.*)$",
        "/${2}"
      ]
    },

    "response-rewrite":{
      "headers":{
        "set": {
            "X-Server-id":3,
            "X-Server-status":"on",
            "X-Server-balancer-addr":"$balancer_ip:$balancer_port"
        }
      },
      "filters":[
        {
          "regex":"href=\"/",
          "scope":"global",
          "replace":"href=\"/httpbin/"
        }
      ],
      "vars":[
        [
          "status",
          "==",
          200
        ]
      ]
    }



  },
  "priority": 10,
  "status": 1,
  "upstream": {
    "hash_on": "vars",
    "nodes": [
      { "host": "127.0.0.1","port": 9991,"weight": 1 }
    ],
    "pass_host": "pass",
    "scheme": "http",
    "timeout": {
      "connect": 6,
      "send": 6,
      "read": 6
    },
    "type": "roundrobin"
  },
  "uri": "/httpbin/*"
}
'

}

fun_install_httpbin
fun_install_httpbin_route

sleep 3s;
curl -iv http://127.0.0.1:9991
curl -iv http://127.0.0.1:9080/httpbin/