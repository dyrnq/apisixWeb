#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

fun_install_httpbin() {
  docker rm -f httpbin &>/dev/null || true ;
  docker run -d --name=httpbin --restart always -p 9992:80 --ulimit nofile=40000:40000 kennethreitz/httpbin
}


fun_install_consumers_and_route(){

curl http://127.0.0.1:9180/apisix/admin/consumers -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
    "username": "jack",
    "plugins": {
        "key-auth": {
            "key": "Gnvl9oyv0y78ujAMDiPftkpanL4WU8k1tO4uDkIQfojC2k124L2dSbnLEpUPvoDoEVt0yWbHiLCTT9YbYUKuYA=="
        }
    }
}'

curl http://127.0.0.1:9180/apisix/admin/routes/9000 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
    "methods": ["GET"],
    "uri": "/get",
    "plugins": {
        "key-auth": {"header": "Authorization"}
    },
    "upstream": {
        "type": "roundrobin",
        "nodes": [
            {"host": "127.0.0.1","port": 9992,"weight": 1}
        ]
    }
}'

}

fun_install_httpbin
fun_install_consumers_and_route


sleep 3s;
curl http://127.0.0.1:9992/get
curl --header "Authorization:Gnvl9oyv0y78ujAMDiPftkpanL4WU8k1tO4uDkIQfojC2k124L2dSbnLEpUPvoDoEVt0yWbHiLCTT9YbYUKuYA==" http://127.0.0.1:9080/get
curl http://127.0.0.1:9080/get?apikey=Gnvl9oyv0y78ujAMDiPftkpanL4WU8k1tO4uDkIQfojC2k124L2dSbnLEpUPvoDoEVt0yWbHiLCTT9YbYUKuYA==
