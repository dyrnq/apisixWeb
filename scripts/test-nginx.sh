#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

nginx_image="${nginx_image:-nginx:1.22.1-alpine}"

fun_install_nginx(){

for i in 1 2 3 4; do
    port=$((i+18080))
    docker rm -f nginx-$i 2>/dev/null || true
    mkdir -p $HOME/nginx/nginx-$i && echo "nginx-$i" > $HOME/nginx/nginx-$i/index.html
    docker run -d --network mynet --restart always -p "${port}":80 --name nginx-$i -v $HOME/nginx/nginx-$i:/usr/share/nginx/html ${nginx_image}
done

}

fun_install_nginx