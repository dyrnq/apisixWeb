# apisixWeb

<img src="https://a.dyrnq.com/apisixWeb/images/logo.png" alt="apisixWeb" width="300" height="300">

## description

apisixWeb is a webui interface of Apisix. This project uses the management API of apisix (that is, the data plane API) to realize the business operation of the apisix instance!

<img src="https://a.dyrnq.com/apisixWeb/images/web/Web%20capture_1-7-2023_132516_192.168.66.100.jpeg" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Web%20capture_1-7-2023_132622_192.168.66.100.jpeg" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Web%20capture_1-7-2023_132652_192.168.66.100.jpeg" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Web%20capture_1-7-2023_132717_192.168.66.100.jpeg" alt="apisixWeb" width="589" height="310">

## build

```bash
git clone git@gitee.com:dyrnq/apisixWeb.git
cd apisixWeb
./mvnw -s ./settings.xml clean package -Dmaven.test.skip=true
```

## run

### install jdk

Ubuntu
```bash
apt update
apt install openjdk-8-jdk -y
# apt install openjdk-11-jdk -y
```

Centos
```bash
yum install -y java-1.8.0-openjdk
# yum install -y java-11-openjdk
```
### install apisix

<https://apisix.apache.org/docs/apisix/installation-guide/>

### run apisixWeb

```bash
wget https://a.dyrnq.com/apisixWeb/f/latest/apisixWeb.jar
```

```bash
java -jar apisixWeb.jar
```
run with more cmd-opts

```bash
java \
-server \
-Xms512m \
-Xmx512m \
-Djava.awt.headless=true \
-Dfile.encoding=UTF-8 \
-Duser.timezone=Asia/Shanghai \
-Djava.net.preferIPv4Stack=true \
-jar apisixWeb.jar \
--server.port=3333 \
--server.session.timeout=172800
```

run with systemd

```bash
vim /etc/systemd/system/apisixWeb.service
```

```bash
[Unit]
Description=apisixWeb
After=syslog.target network.target

[Service]
Type=simple
Environment="PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/java/bin"
TimeoutStartSec=30
TimeoutStopSec=20
ExecStart=/usr/bin/java \
  -server \
  -Xms512m \
  -Xmx512m \
  -Dfile.encoding=UTF-8 \
  -Duser.timezone=Asia/Shanghai \
  -Djava.net.preferIPv4Stack=true \
  -jar /data/apisixWeb/apisixWeb.jar \
  --server.port=3333 \
  --server.session.timeout=172800
ExecStop=/bin/kill -s TERM $MAINPID
Restart=always
LimitNOFILE=infinity
LimitNPROC=infinity
LimitCORE=infinity
TasksMax=infinity
[Install]
WantedBy=multi-user.target
```

```bash
systemctl daemon-reload
if systemctl is-active apisixWeb &>/dev/null; then
    systemctl restart apisixWeb
else
    systemctl enable --now apisixWeb
fi
systemctl status -l apisixWeb --no-pager
```

Change default session jwt secret

```bash
java -cp apisixWeb.jar cli jwt
```

Then pass `--server.session.state.jwt.secret=your-secret` to `java -jar`.


Parameter description (all are optional)

| Parameter                    | Meaning                                        | Default         |
|------------------------------|------------------------------------------------|-----------------|
| --server.port                | http server port                               | 8080            |
| --project.home               | project home dir, storing db, cert, logs, etc. | $HOME/apisixWeb |
| --spring.database.type       | optional mysql,sqlite,postgresql               | h2              |
| --spring.datasource.url      | datasource url                                 |                 |
| --spring.datasource.username | datasource username                            |                 |
| --spring.datasource.password | datasource password                            |                 |





