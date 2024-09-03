#!/usr/bin/env bash

[ "$(uname -m)" = "x86_64" ] && arch="amd64";
[ "$(uname -m)" = "aarch64" ] && arch="arm64";
url="https://github.com/dyrnq/aia/releases/download/v0.0.2/aia-linux-${arch}"
# url=${url/github.com/mirror.ghproxy.com/github.com}
url=${url/github.com/files.m.daocloud.io/github.com}

echo $url;
service="aia"

systemctl disable --now ${service}.service 2>/dev/null || true

curl -f#SL -o /usr/local/bin/aia $url
#cp -v -f /vagrant/aia /usr/local/bin/aia
chmod +x /usr/local/bin/aia
aia --version


cat > /lib/systemd/system/${service}.service << EOF
[Unit]
Description=aia server
After=syslog.target network.target

[Service]
Type=simple
ExecStart=/usr/local/bin/aia \
--listen :5980 \
--apisix-config /home/vagrant/apisix/conf/config.yaml \
--apisix-reload-cmd "docker exec -t apisix bash -c \"apisix reload\"" \
--apisix-stop-cmd "docker stop apisix || true" \
--apisix-start-cmd "docker start apisix" \
--apisix-restart-cmd "docker restart apisix"
TimeoutStartSec=30
TimeoutStopSec=20
ExecStop=/bin/kill -s TERM \$MAINPID
LimitNOFILE=infinity
LimitNPROC=infinity
LimitCORE=infinity
TasksMax=infinity
LimitNOFILE=512000
LimitNPROC=512000
Restart=always
[Install]
WantedBy=multi-user.target
EOF


systemctl daemon-reload

if systemctl is-active "${service}" &>/dev/null; then
    systemctl restart "${service}"
else
    systemctl enable --now "${service}"
fi



