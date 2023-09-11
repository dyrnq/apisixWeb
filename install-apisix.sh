#!/usr/bin/env bash



iface="${iface:-enp0s8}"
etcd_home="${etcd_home:-$HOME/etcd}"
apisix_home="${apisix_home:-$HOME/apisix}"
apisix_dashboard_home="${apisix_dashboard_home:-$HOME/apisix-dashboard}"
apisix_image="${apisix_image:-apache/apisix:3.5.0-debian}"
#apisix_image="${apisix_image:-bitnami/apisix:3.5.0-debian-11-r0}"
apisix_dashboard_image="${apisix_dashboard_image:-apache/apisix-dashboard:3.0.1-alpine}"
etcd_image="${etcd_image:-quay.io/coreos/etcd:v3.5.9}"
wait4x_image="${wait4x_image:-atkrad/wait4x:2.12}"
nginx_image="${nginx_image:-nginx:1.22.1-alpine}"
mysql5_image="${mysql5_image:-mysql:5.7.41}"
mysql8_image="${mysql8_image:-mysql:8.0.23}"
pg_image="${pg_image:-postgres:12.14}"
whoami_image="${whoami_image:-containous/whoami:latest}"
adminer_image="${adminer_image:-adminer:4.8.1}"
minio_image="${minio_image:-minio/minio:RELEASE.2022-11-29T23-40-49Z}"


while [ $# -gt 0 ]; do
    case "$1" in
        --iface|-i)
            iface="$2"
            shift
            ;;
        --apisix-home|-h)
            apisix_home="$2"
            shift
            ;;
        --apisix-dashboard-home|-H)
            apisix_dashboard_home="$2"
            shift
            ;;
        --apisix-image|-m)
            apisix_image="$2"
            shift
            ;;
        --apisix-dashboard-image|-M)
            apisix_dashboard_image="$2"
            shift
            ;;
        --*)
            echo "Illegal option $1"
            ;;
    esac
    shift $(( $# > 0 ? 1 : 0 ))
done

ip4=$(/sbin/ip -o -4 addr list "${iface}" | awk '{print $4}' |cut -d/ -f1 | head -n1);


command_exists() {
    command -v "$@" > /dev/null 2>&1
}


fun_install_etcd(){
mkdir -p "$etcd_home"/etc/etcd
mkdir -p "$etcd_home"/var/lib/etcd

cat > "$etcd_home"/etc/etcd/etcd.conf.yml <<EOF
name: 'default'
data-dir: /var/lib/etcd
enable-v2: false
debug: false
logger: zap
log-outputs: [stderr]
listen-client-urls: http://0.0.0.0:2379
EOF


docker rm -f etcd 2>/dev/null || true
docker run -d --name etcd \
--restart always \
--net host \
--privileged \
--ulimit nofile=40000:40000 \
-v "$etcd_home"/etc/etcd:/etc/etcd \
-v "$etcd_home"/var/lib/etcd:/var/lib/etcd \
${etcd_image} etcd --config-file /etc/etcd/etcd.conf.yml
}



fun_install() {
mkdir -p ${apisix_home}/
mkdir -p ${apisix_home}/conf
mkdir -p ${apisix_dashboard_home}/conf

if [ ! -f ${apisix_home}/dhparam.pem ]; then
  #openssl dhparam -out ${apisix_home}/dhparam.pem 4096
  openssl dhparam -dsaparam -out ${apisix_home}/dhparam.pem 4096
fi

## https://github.com/apache/apisix/blob/master/conf/config.yaml
cat > ${apisix_home}/conf/config.yaml <<EOF
apisix:
  node_listen:
    - 9080
    - 80
  proxy_mode: "http&stream"
  ssl:
    enable: true
    listen:
      - port: 9443
        enable_http2: true
      - port: 443
        enable_http2: true
  enable_ipv6: false
  stream_proxy:
      tcp:
        - 9100
nginx_config:
#  main_configuration_snippet: |
#    user apisix apisix;
  http_configuration_snippet: |
    ssl_dhparam /etc/ssl/certs/dhparam.pem;
    server{
        listen 45651;
        server_name _;
        access_log off;
        location / {
            root /usr/local/apisix/conf;
            index index.html index.htm;
            autoindex on;
            autoindex_exact_size on;
            autoindex_localtime on;
            types {
                text/plain yaml;
                text/plain md;
                text/plain yml;
                text/plain conf;
                text/plain properties;
                text/plain service;
                text/plain sh;
                text/plain sed;
            }
        }
    }

deployment:
  role: traditional
  role_traditional:
    config_provider: etcd

  etcd:
    host: ["http://$ip4:2379"]
    prefix: "/apisix"
    timeout: 30
  admin:
    allow_admin:
      - 192.168.0.0/16
      - 127.0.0.0/24  
    admin_key:
      - name: admin
        key: edd1c9f034335f136f87ad84b625c8f1  # using fixed API token has security risk, please update it when you deploy to production environment
        role: admin

plugin_attr:
  prometheus:
    export_addr:
      ip: "0.0.0.0"
      port: 9091

discovery:                       # service discovery center
  dns:
    servers:
      - "127.0.0.1:8600"         # use the real address of your dns server
  consul:
    fetch_interval: 1
    keepalive: true
    # timeout:
    #   connect: 20
    #   read: 10
    #   wait: 10
    servers:
      - "http://127.0.0.1:8500"
EOF




docker rm -f apisix 2>/dev/null || true
if grep "bitnami" <<< ${apisix_image}; then
docker run --net host --rm --name='wait4x' ${wait4x_image} tcp -i 1s -q -t 5s 127.0.0.1:2379 && \
docker run -d --name apisix \
--restart always \
--net host \
--privileged \
-u root \
--ulimit nofile=40000:40000 \
-v ${apisix_home}/dhparam.pem:/etc/ssl/certs/dhparam.pem \
-v ${apisix_home}/conf/config.yaml:/usr/local/apisix/conf/config.yaml  \
--entrypoint="" \
${apisix_image} \
sh -c "apisix init && apisix init_etcd && exec openresty -p /usr/local/apisix -g 'daemon off;'"
else
docker run --net host --rm --name='wait4x' ${wait4x_image} tcp -i 1s -q -t 5s 127.0.0.1:2379 && \
docker run -d --name apisix \
--restart always \
--net host \
--privileged \
-u root \
--ulimit nofile=40000:40000 \
-v ${apisix_home}/dhparam.pem:/etc/ssl/certs/dhparam.pem \
-v ${apisix_home}/conf/config.yaml:/usr/local/apisix/conf/config.yaml  \
${apisix_image}
fi





secret=$(openssl rand -base64 32)
secret="secret"
## https://github.com/apache/apisix-dashboard/blob/master/api/conf/conf.yaml
cat > ${apisix_dashboard_home}/conf/conf.yaml <<EOF
conf:
  listen:
    host: 0.0.0.0
    port: 9000
  allow_list:
    - 0.0.0.0/0
  etcd:
    endpoints: ["http://$ip4:2379"]
    prefix: /apisix
  log:
    error_log:
      level: warn
      file_path:
        /dev/stderr
    access_log:
      file_path:
        /dev/stdout
  max_cpu: 0
  
authentication:
  secret:
    ${secret}
  expire_time: 36000
  users:
    - username: admin
      password: admin
plugins:
  - api-breaker
  - authz-keycloak
  - basic-auth
  - batch-requests
  - consumer-restriction
  - cors
  # - dubbo-proxy
  - echo
  # - error-log-logger
  # - example-plugin
  - fault-injection
  - grpc-transcode
  - hmac-auth
  - http-logger
  - ip-restriction
  - jwt-auth
  - kafka-logger
  - key-auth
  - limit-conn
  - limit-count
  - limit-req
  # - log-rotate
  # - node-status
  - openid-connect
  - prometheus
  - proxy-cache
  - proxy-mirror
  - proxy-rewrite
  - redirect
  - referer-restriction
  - request-id
  - request-validation
  - response-rewrite
  - serverless-post-function
  - serverless-pre-function
  # - skywalking
  - sls-logger
  - syslog
  - tcp-logger
  - udp-logger
  - uri-blocker
  - wolf-rbac
  - zipkin
  - server-info
  - traffic-split
  - elasticsearch-logge
  - openfunction
  - tencent-cloud-cls
  - ai
  - cas-auth
EOF


 docker rm -f apisix-dashboard 2>/dev/null || true
 docker run -d --name apisix-dashboard \
 --restart always \
 --net host \
 --privileged \
 -e TZ=Asia/Shanghai \
 -v ${apisix_dashboard_home}/conf/conf.yaml:/usr/local/apisix-dashboard/conf/conf.yaml  \
 ${apisix_dashboard_image}


}

fun_add_mynet(){

docker network inspect mynet &>/dev/null || docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 --driver bridge mynet

}

fun_install_nginx(){

for i in 1 2 3 4; do
    port=$((i+18080))
    docker rm -f nginx-$i 2>/dev/null || true
    mkdir -p $HOME/nginx/nginx-$i && echo "nginx-$i" > $HOME/nginx/nginx-$i/index.html
    docker run -d --network mynet --restart always -p "${port}":80 --name nginx-$i -v $HOME/nginx/nginx-$i:/usr/share/nginx/html ${nginx_image}
done

}

fun_install_misc(){
docker rm -f mysql57 2>/dev/null || true
docker rm -f mysql8 2>/dev/null || true
docker rm -f postgres12 2>/dev/null || true
docker rm -f adminer 2>/dev/null || true


mkdir -p $HOME/var/lib/mysql
docker run -d --name mysql57 \
--restart always \
--network mynet \
-e MYSQL_ROOT_PASSWORD=666666 \
-v $HOME/var/lib/mysql:/var/lib/mysql \
-p 3306:3306 \
${mysql5_image} --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --default-time-zone=+8:00

mkdir -p $HOME/var/lib/mysql8
docker run -d --name mysql8 \
--restart always \
--network mynet \
-e MYSQL_ROOT_PASSWORD=666666 \
-v $HOME/var/lib/mysql8:/var/lib/mysql \
-p 13306:3306 \
${mysql8_image} --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --default-time-zone=+8:00 --innodb-dedicated-server=on


mkdir -p $HOME/var/lib/postgresql/data

docker run -d --name postgres12 \
--restart always \
--network mynet \
-e POSTGRES_PASSWORD=666666 \
-p 5432:5432 \
-v $HOME/var/lib/postgresql/data:/var/lib/postgresql/data \
${pg_image}


docker run -d --name=adminer --restart always --network mynet -p 18080:8080 ${adminer_image}


for num in {1..7}; do
name="w${num}"
port=$((6680+num-1))
docker rm -f "${name}" &>/dev/null || true ;
docker run -d --name "${name}" --restart always --network mynet -p "${port}":80 ${whoami_image};
done
}

fun_initdb(){

docker run -it --rm --network mynet  ${mysql5_image} mysql --host mysql57 --user root --password=666666 --loose-default-character-set=utf8 -e "CREATE DATABASE if not exists apisixweb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;show databases;"
docker run -it --rm --network mynet  ${mysql8_image} mysql --host mysql8 --user root --password=666666 -e "CREATE DATABASE if not exists apisixweb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;show databases;"
docker exec -i postgres12 bash <<'EOF'
if [ $(psql -tA --username "postgres" -c "select count(1) from pg_database where datname='apisixweb'") = "0" ]; then
    psql -v ON_ERROR_STOP=1 --username "postgres" --no-password -c "create database apisixweb with encoding='utf8' TEMPLATE template0;"
fi
EOF
}

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



curl http://127.0.0.1:9180/apisix/admin/routes/9008 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -i -d '
{
  "name": "minio-test",
  "plugins": {
    "proxy-rewrite": {
      "use_real_request_uri_unsafe": false,
      "regex_uri": [
        "^/(.*)$",
        "/test/${1}"
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
  "uri": "/*"
}'

curl -fsSL http://127.0.0.1:19000/test/bashrc | head -n5
curl -fsSL http://127.0.0.1:9080/bashrc | head -n5

}
fun_install_httpbin() {
  docker rm -f httpbin &>/dev/null || true ;
  docker run -d --name=httpbin --restart always --network host --privileged --ulimit nofile=40000:40000 mccutchen/go-httpbin go-httpbin -port 9991
}

fun_add_mynet
fun_install_nginx
fun_install_misc
fun_install_etcd
fun_install
fun_initdb
fun_install_minio
fun_install_httpbin