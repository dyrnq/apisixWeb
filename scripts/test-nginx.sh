#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

nginx_image="${nginx_image:-nginx:1.22.1-alpine}"

fun_install_nginx(){

cat >/tmp/default.conf <<EOF
server {
    listen       80;
    listen  [::]:80;
    server_name  localhost;


    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;
    }


    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

    real_ip_header X-Forwarded-For;
    real_ip_recursive on;
    set_real_ip_from  172.0.0.0/8;
    set_real_ip_from  192.168.1.0/24;
    set_real_ip_from  192.168.2.1;
    set_real_ip_from  2001:0db8::/32;
    ### http://nginx.org/en/docs/http/ngx_http_realip_module.html
}
EOF

for i in 1 2 3 4; do
    port=$((i+18080))
    docker rm -f nginx-$i 2>/dev/null || true
    mkdir -p $HOME/nginx/nginx-$i && echo "nginx-$i" > $HOME/nginx/nginx-$i/index.html
    docker run -d --network mynet -v /tmp/default.conf:/etc/nginx/conf.d/default.conf --restart always -p "${port}":80 --name nginx-$i -v $HOME/nginx/nginx-$i:/usr/share/nginx/html ${nginx_image}
done

}


fun_install_nginx_route() {

curl http://127.0.0.1:9180/apisix/admin/routes/8001 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
  "name": "nginx",
  "plugins": {

  },
  "priority": 10,
  "status": 1,
  "upstream": {
    "hash_on": "vars",
    "nodes": [
      { "host": "127.0.0.1","port": 18081,"weight": 1},
      { "host": "127.0.0.1","port": 18082,"weight": 1},
      { "host": "127.0.0.1","port": 18083,"weight": 1},
      { "host": "127.0.0.1","port": 18084,"weight": 1}      
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
}
'
}


fun_install_nginx
fun_install_nginx_route
for num in {1..5}; do
  echo -n "${num}---------->" && sleep 1s;
  curl -fsSL http://127.0.0.1:9080/
done
for num in {1..5}; do
  echo -n "${num}---------->" && sleep 1s;
  curl -fsSL http://192.168.66.100:9080/
done

docker logs nginx-1