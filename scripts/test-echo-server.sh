#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

image="${image:-ealen/echo-server:latest}"

fun_install() {
for num in {1..3}; do
    name="echo${num}"
    port=$((7781+num-1))
    docker rm -f "${name}" &>/dev/null || true ;
    docker run -d --name "${name}" --restart always --network mynet -p "${port}":80 ${image};
done
}

fun_install_route() {

curl http://127.0.0.1:9180/apisix/admin/routes/9003 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
  "name": "echo",
  "plugins": {
    "proxy-rewrite": {
      "use_real_request_uri_unsafe": false,
      "regex_uri": [
        "^/([^/]+)/(.*)$",
        "/${2}"
      ]
    }
  },
  "priority": 10,
  "status": 1,
  "upstream": {
    "hash_on": "vars",
    "nodes": [
      { "host": "127.0.0.1","port": 7781,"weight": 1},
      { "host": "127.0.0.1","port": 7782,"weight": 1},
      { "host": "127.0.0.1","port": 7783,"weight": 1}
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
  "uri": "/echo/*"
}
'
}

fun_install
fun_install_route

curl -fsSL http://127.0.0.1:9080/echo/ | jq
curl -fsSL http://127.0.0.1:9080/echo/?echo_body=amazing
curl -fsSL http://127.0.0.1:9080/echo/param?query=demo | jq
curl --header 'X-ECHO-HEADER: One:1' -i http://127.0.0.1:9080/echo/
curl --header 'X-ECHO-HEADER: One:1' -i http://127.0.0.1:9080/echo/
## https://github.com/Ealenn/Echo-Server