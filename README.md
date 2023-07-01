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

```bash
java -jar apisixWeb.jar
```



Parameter description (all are optional)

| Parameter                    | Meaning                                                                       | Default         |
|------------------------------|-------------------------------------------------------------------------------|-----------------|
| --server.port                | port                                                                          | 8080            |
| --project.home               | project home directory, storing database files, certificate files, logs, etc. | $HOME/apisixWeb |
| --spring.database.type       | use another database, optional mysql,sqlite,postgresql                        | h2              |
| --spring.datasource.url      | datasource url                                                                |                 |
| --spring.datasource.username | datasource username                                                           |                 |
| --spring.datasource.password | datasource password                                                           |                 |





