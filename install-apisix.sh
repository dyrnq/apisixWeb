#!/usr/bin/env bash
# shellcheck disable=SC2086


iface="${iface:-enp0s8}"
etcd_home="${etcd_home:-$HOME/etcd}"
apisix_home="${apisix_home:-$HOME/apisix}"
apisix_dashboard_home="${apisix_dashboard_home:-$HOME/apisix-dashboard}"
apisix_image="${apisix_image:-apache/apisix:3.13.0-debian}"
#apisix_image="${apisix_image:-bitnami/apisix:3.13.0-debian-12-r1}"
apisix_dashboard_image="${apisix_dashboard_image:-apache/apisix-dashboard:3.0.1-alpine}"
etcd_image="${etcd_image:-quay.io/coreos/etcd:v3.5.13}"
wait4x_image="${wait4x_image:-atkrad/wait4x:2.14}"
mysql5_image="${mysql5_image:-mysql:5.7.41}"
mysql8_image="${mysql8_image:-mysql:8.0.23}"
pg_image="${pg_image:-postgres:12.14}"
adminer_image="${adminer_image:-adminer:4.8.1}"



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
--ulimit nofile=40000:40000 \
-v "$etcd_home"/etc/etcd:/etc/etcd \
-v "$etcd_home"/var/lib/etcd:/var/lib/etcd \
${etcd_image} etcd --config-file /etc/etcd/etcd.conf.yml
}



fun_install_apisix() {
mkdir -p ${apisix_home}/
mkdir -p ${apisix_home}/conf


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
      - port: 443
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
-u root \
--ulimit nofile=40000:40000 \
-v /etc/localtime:/etc/localtime:ro \
-v ${apisix_home}/dhparam.pem:/etc/ssl/certs/dhparam.pem \
-v ${apisix_home}/conf/config.yaml:/usr/local/apisix/conf/config.yaml  \
${apisix_image}
fi

}

fun_install_apisix_dashboard(){
mkdir -p ${apisix_dashboard_home}/conf

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
 -e TZ=Asia/Shanghai \
 -v ${apisix_dashboard_home}/conf/conf.yaml:/usr/local/apisix-dashboard/conf/conf.yaml  \
 ${apisix_dashboard_image}


}

fun_add_mynet(){

docker network inspect mynet &>/dev/null || docker network create --subnet 172.18.0.0/16 --gateway 172.18.0.1 --driver bridge mynet

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



}

fun_initdb(){

docker run --net host --rm --name='wait4x' ${wait4x_image} mysql root:666666@tcp\(127.0.0.1:3306\)/mysql --interval 1s --timeout 360s && \
docker run -it --rm --network mynet  ${mysql5_image} mysql --host mysql57 --user root --password=666666 --loose-default-character-set=utf8 -e "CREATE DATABASE if not exists apisixweb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;show databases;"


docker run --net host --rm --name='wait4x' ${wait4x_image} mysql root:666666@tcp\(127.0.0.1:13306\)/mysql --interval 1s --timeout 360s && \
docker run -it --rm --network mynet  ${mysql8_image} mysql --host mysql8 --user root --password=666666 -e "CREATE DATABASE if not exists apisixweb DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;show databases;"


docker run --net host --rm --name='wait4x' ${wait4x_image} postgresql 'postgres://postgres:666666@127.0.0.1:5432/postgres?sslmode=disable' --interval 1s --timeout 360s && \
docker exec -i postgres12 bash <<'EOF'
if [ $(psql -tA --username "postgres" -c "select count(1) from pg_database where datname='apisixweb'") = "0" ]; then
    psql -v ON_ERROR_STOP=1 --username "postgres" --no-password -c "create database apisixweb with encoding='utf8' TEMPLATE template0;"
fi
psql -v ON_ERROR_STOP=1 --username "postgres" --no-password -c "\l";
EOF
}




fun_add_mynet


fun_install_etcd
fun_install_apisix
fun_install_apisix_dashboard

fun_install_misc
fun_initdb

