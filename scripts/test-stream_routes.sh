#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

mysql5_image="${mysql5_image:-mysql:5.7.41}"
mysql8_image="${mysql8_image:-mysql:8.0.23}"

fun_install_streamroute(){

curl http://127.0.0.1:9180/apisix/admin/stream_routes/100001 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
  "upstream": {
    "nodes": [
      { "host": "127.0.0.1","port": 3306 ,"weight": 1 }
    ],
    "pass_host": "pass",
    "timeout": {
      "connect": 6,
      "send": 6,
      "read": 6
    },
    "type": "roundrobin"
  },
  "server_port": 9100
}
'

}

fun_install_streamroute

docker run -it \
--rm \
--network mynet \
--add-host="host.docker.internal:host-gateway" \
${mysql5_image} \
mysql --host host.docker.internal --port 9100 --user root --password=666666 --loose-default-character-set=utf8 -e "SHOW VARIABLES LIKE '%char%'"