#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

whoami_image="${whoami_image:-containous/whoami:latest}"

fun_install_whoami() {
for num in {1..7}; do
    name="w${num}"
    port=$((6680+num-1))
    docker rm -f "${name}" &>/dev/null || true ;
    docker run -d --name "${name}" --restart always --network mynet -p "${port}":80 ${whoami_image};
done
}

fun_install_whoami_route() {

curl http://127.0.0.1:9180/apisix/admin/routes/9002 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
  "name": "whoami",
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
      { "host": "127.0.0.1","port": 6680,"weight": 1},
      { "host": "127.0.0.1","port": 6681,"weight": 1},
      { "host": "127.0.0.1","port": 6682,"weight": 1},
      { "host": "127.0.0.1","port": 6683,"weight": 1},
      { "host": "127.0.0.1","port": 6684,"weight": 1},
      { "host": "127.0.0.1","port": 6685,"weight": 1},
      { "host": "127.0.0.1","port": 6686,"weight": 1}
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
  "uri": "/whoami/*"
}
'
}

fun_install_whoami
fun_install_whoami_route

curl -iv http://127.0.0.1:9080/whoami/

