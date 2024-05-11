#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

minio_image="${minio_image:-minio/minio:RELEASE.2022-11-29T23-40-49Z}"

fun_install_minio(){
docker rm -f minio 2>/dev/null || true
mkdir -p $HOME/minio/data
docker run -d --name=minio --restart always --network host \
-e "MINIO_ROOT_USER=minioadmin" \
-e "MINIO_ROOT_PASSWORD=minioadmin" \
-v $HOME/minio/data:/data \
${minio_image} server /data --address ":19000" --console-address ":19001"

# WARNING: MINIO_ACCESS_KEY and MINIO_SECRET_KEY are deprecated.
#-e "MINIO_ACCESS_KEY=u5SybesIDVX9b6Pk"
#-e "MINIO_SECRET_KEY=lOpH1v7kdM6H8NkPu1H2R6gLc9jcsmWM"

sleep 5s;

docker run -i --rm --network host --entrypoint '' quay.io/minio/mc bash<<EOF
mc alias set myminio http://localhost:19000 minioadmin minioadmin;
mc admin user svcacct add --access-key "u5SybesIDVX9b6Pk" --secret-key "lOpH1v7kdM6H8NkPu1H2R6gLc9jcsmWM" myminio minioadmin 2>/dev/null || true;
EOF



docker run -i --rm --network host --entrypoint '' quay.io/minio/mc bash<<EOF
mc alias set myminio http://localhost:19000 minioadmin minioadmin;
mc mb myminio/test 2>/dev/null || true;
mc anonymous set download myminio/test;
mc cp --attr "content-type=text/javascript" /etc/bashrc myminio/test;
EOF
}

fun_install_minio_route() {

curl http://127.0.0.1:9180/apisix/admin/routes/9008 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -i -d '
{
  "name": "minio-test",
  "plugins": {
    "proxy-rewrite": {
      "use_real_request_uri_unsafe": false,
      "regex_uri": [
          "^/([^/]+)/(.*)$",
          "/test/${2}"
      ]
    }
  },
  "priority": 10,
  "status": 1,
  "upstream": {
    "hash_on": "vars",
    "nodes": [
      {
        "host": "127.0.0.1",
        "port": 19000,
        "weight": 1
      }
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
  "uri": "/minio/*"
}'

}

fun_install_minio
fun_install_minio_route


curl -fsSL http://127.0.0.1:19000/test/bashrc | head -n5
curl -fsSL http://127.0.0.1:9080/minio/bashrc | head -n5