# apisixWeb

<img src="https://a.dyrnq.com/apisixWeb/images/logo.png" alt="apisixWeb" width="300" height="300">

<!-- TOC -->

- [apisixWeb](#apisixweb)
  - [描述](#描述)
  - [特色](#特色)
  - [构建](#构建)
  - [运行](#运行)
    - [安装 jdk](#安装-jdk)
    - [安装 apisix](#安装-apisix)
    - [运行 apisixWeb](#运行-apisixweb)

<!-- /TOC -->

## 描述

apisixWeb是一个Apisix的webui界面，该项目使用apisix的管理API（即数据面API）实现了对apisix实例的业务操作!

最终目的是使用Apisix来接管网站的流量端口80和443。使用最佳实践进行网站流量管理，既高效又方便。

灵感来自 [nginxWebUI](https://gitee.com/cym1102/nginxWebUI)。

## 特色

- 增加，删除，修改 Routes，StreamRoutes，SSL... 基于Apisix数据面APIs。
- 多个Apisix实例管理。
- 批量操作。
- 一键删除。
- 原始数据导入导出。
- 使用acme.sh实现https证书的申请（DNS-01）。
- 使用acme4j实现https证书的申请（HTTP-01）。
- 自签名证书管理。
- 过期证书自动重新申请。
- 使用yaml文件来实现规则的部署。

<img src="https://a.dyrnq.com/apisixWeb/images/web/Screenshot%202023-09-23%20at%2010-10-33%20apisixWeb.png" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Screenshot%202023-09-23%20at%2010-11-05%20apisixWeb.png" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Screenshot%202023-09-23%20at%2010-11-24%20apisixWeb.png" alt="apisixWeb" width="589" height="310">

<img src="https://a.dyrnq.com/apisixWeb/images/web/Screenshot%202023-09-23%20at%2010-11-46%20apisixWeb.png" alt="apisixWeb" width="589" height="310">

## 构建

```bash
git clone git@gitee.com:dyrnq/apisixWeb.git
cd apisixWeb
./mvnw -s ./settings.xml clean package -Dmaven.test.skip=true
```

## 运行

### 安装 jdk

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

### 安装 apisix

<https://apisix.apache.org/docs/apisix/installation-guide/>

### 运行 apisixWeb

```bash
wget https://a.dyrnq.com/apisixWeb/f/latest/apisixWeb.jar
```

```bash
java -jar apisixWeb.jar
```

使用命令行方式的示例

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

使用systemd部署的示例

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

更改默认 session jwt secret

```bash
java -cp apisixWeb.jar cli jwt
```

将参数 `--jwt.secret=your-secret` 增加到 `java -jar`。

参数描述 (都是可选的）

| 参数                         | 含义                               | 默认值          |
|------------------------------|------------------------------------|-----------------|
| --server.port                | 端口                               | 8080            |
| --project.home               | 数据目录                           | $HOME/apisixWeb |
| --spring.database.type       | 可选 h2，mysql，sqlite，postgresql | h2              |
| --spring.datasource.url      | 数据源 url                         |                 |
| --spring.datasource.username | 数据源 username                    |                 |
| --spring.datasource.password | 数据源 password                    |                 |
