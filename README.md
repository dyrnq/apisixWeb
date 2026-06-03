# apisixWeb

<img src="https://a.dyrnq.com/apisixWeb/images/logo.png" alt="apisixWeb" width="300" height="300">

<!-- TOC -->

- [apisixWeb](#apisixweb)
  - [description](#description)
  - [features](#features)
  - [docker](#docker)
  - [build](#build)
  - [run](#run)
    - [install jdk](#install-jdk)
    - [install apisix](#install-apisix)
    - [run apisixWeb](#run-apisixweb)

<!-- /TOC -->

## description

apisixWeb is a webui interface of Apisix. This project uses the management API of apisix (that is, the data plane API) to realize the business operation of the apisix instance!

The ultimate purpose is using Apisix to take over the traffic ports of the website, 80 and 443. Use best practices for website traffic management, efficiently and conveniently.

Inspired by [nginxWebUI](https://gitee.com/cym1102/nginxWebUI).

## features

- Add, delete, modify Routes, StreamRoutes, SSL... based on Apisix management APIs.
- Multiple Apisix instance management.
- Batch delete rules.
- Clear all rules with one click.
- Rawdata import and export.
- Implementing DNS-01 certificate application using acme.sh.
- Implementing HTTP-01 certificate application with acme4j.
- Self signed certificate management.
- Automatic renewal of expired certificates.
- Deploy management based on yaml files.

<img src="https://a.dyrnq.com/apisixWeb/images/web/Screenshot%202023-09-23%20at%2010-10-33%20apisixWeb.png" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Screenshot%202023-09-23%20at%2010-11-05%20apisixWeb.png" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Screenshot%202023-09-23%20at%2010-11-24%20apisixWeb.png" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Screenshot%202023-09-23%20at%2010-11-46%20apisixWeb.png" alt="apisixWeb" width="589" height="310">

## docker

```bash
# create persistence data directory for apisixWeb
mkdir -p $HOME/apisixWeb
chown -R 1000:1000 $HOME/apisixWeb
# gen a new jwt secret replace default IDP32XTulsVIUZU+srFEUC9Lhu1wV+nd8iCJPoPA2zSFVAtWhCgpMEymxy5wFAZKMB9yROX31UjDzjwL66r1RA==
docker run -it --rm --entrypoint="" dyrnq/apisixweb:latest-jre21 bash -c "java -jar /app/apisixWeb.jar cli jwt"

# rm old apisixweb continer
docker rm -f apisixweb >/dev/null 2>&1

# run new apisixweb continer
docker run \
--name apisixweb \
-d \
--restart always \
-v $HOME/apisixWeb:/app/apisixWeb \
-p 8080:8080 \
-e TZ="Asia/Shanghai" \
-e SERVER_PORT="8080" \
-e SERVER_SESSION_TIMEOUT="172800" \
-e JWT_SECRET="IDP32XTulsVIUZU+srFEUC9Lhu1wV+nd8iCJPoPA2zSFVAtWhCgpMEymxy5wFAZKMB9yROX31UjDzjwL66r1RA==" \
-e JAVA_OPTS="-server -Xms512m -Xmx512m -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.net.preferIPv4Stack=true" \
dyrnq/apisixweb:latest-jre21
```

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
java -jar apisixWeb-1.0.0.jar cli jwt
```

Then pass `--jwt.secret=your-secret` to `java -jar`.

Parameter description (all are optional)

| Parameter                    | Meaning                                        | Default         |
|------------------------------|------------------------------------------------|-----------------|
| --server.port                | http server port                               | 8080            |
| --project.home               | project home dir, storing db, cert, logs, etc. | $HOME/apisixWeb |
| --spring.database.type       | optional h2, mysql, sqlite, postgresql         | h2              |
| --spring.datasource.url      | datasource url                                 |                 |
| --spring.datasource.username | datasource username                            |                 |
| --spring.datasource.password | datasource password                            |                 |


Supports environment variables

| Variable Name                        | Meaning                                       | Default Value    |
|--------------------------------------|-----------------------------------------------|------------------|
| SERVER_PORT                          | Server port                                   | 8080             |
| PROJECT_HOME                         | Data directory                                | $HOME/apisixWeb  |
| SPRING_DATABASE_TYPE                 | Database type (h2, mysql, sqlite, postgresql) | h2               |
| SPRING_DATASOURCE_URL                | Database URL                                  |                  |
| SPRING_DATASOURCE_USERNAME           | Database username                             |                  |
| SPRING_DATASOURCE_PASSWORD           | Database password                             |                  |
| JWT_SECRET                           | jwt secret                                    |                  |
| SERVER_SESSION_TIMEOUT                | session timeout                               | 7200             |