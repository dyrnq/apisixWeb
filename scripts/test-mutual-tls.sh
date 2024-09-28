#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

set -eu -o pipefail


SCRIPT_DIR=$(cd "$(dirname "${BASH_SOURCE[0]}")" > /dev/null 2>&1 && pwd -P)

fun_line(){

(
  echo "";
  echo "";
  echo "";

)

}

fun_prepare_env(){

command -v git > /dev/null 2>&1 || ( echo "ERROR please install git" && exit 1 )
command -v java > /dev/null 2>&1 || ( echo "ERROR please install java" && exit 1 )
command -v mvn > /dev/null 2>&1 || ( echo "ERROR please install maven" && exit 1 )

git --version
java --version
mvn --version

test -e $HOME/.m2 || mkdir -p $HOME/.m2
cat > $HOME/.m2/settings.xml<<EOF
<?xml version="1.0" encoding="UTF-8"?>


<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">



  <pluginGroups>
  </pluginGroups>

  <proxies>
  </proxies>

  <servers>

  </servers>

  <mirrors>
    <mirror>
        <id>aliyunmaven</id>
        <mirrorOf>central</mirrorOf>
        <name>阿里云公共仓库</name>
        <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>

  <profiles>

  </profiles>

</settings>
EOF


}

fun_build_mutual-tls-ssl(){

pushd $HOME || exit 1

test -e $HOME/mutual-tls-ssl || git clone https://github.com/Hakky54/mutual-tls-ssl.git
  pushd mutual-tls-ssl || exit 1
    mvn clean package -Dmaven.test.skip=true
  popd || exit 1
popd || exit 1

}


fun_gen_certs() {
if ! command -v cfssl > /dev/null 2>&1; then
  mkdir -p $HOME/bin
  bash $SCRIPT_DIR/install-cfssl.sh -d $HOME/bin -m ghproxy
  export PATH=$PATH:$HOME/bin
fi
echo $PATH


mkdir -p $HOME/opt/certs

pushd $HOME/opt/certs || exit 1
rm -rf ./*
cat > config.json <<EOF
{
  "signing": {
    "default": {
      "expiry": "876000h"
    },
    "profiles": {
      "kubernetes": {
        "usages": ["signing", "key encipherment", "server auth", "client auth"],
        "expiry": "876000h"
      },
      "client": {
        "usages": ["signing", "key encipherment", "client auth"],
        "expiry": "876000h"
      }
    }
  }
}
EOF


cat > ca-csr.json <<EOF
{
  "CN": "root-ca",
  "key": {
    "algo": "rsa",
    "size": 2048
  },
  "names": [
    {
      "C": "CN",
      "ST": "BeiJing",
      "L": "BeiJing",
      "O": "k8s",
      "OU": "dyrnq"
    }
  ],
  "ca": {
    "expiry": "876000h"
 }
}
EOF

cat > server-csr.json <<EOF
{
  "CN": "tomcat",
  "hosts": [
    "127.0.0.1",
    "192.168.1.101",
    "tomcat",
    "tomcat.default",
    "tomcat.default.svc",
    "tomcat.local"
  ],
  "key": {
    "algo": "rsa",
    "size": 2048
  },
  "names": [
    {
      "C": "CN",
      "ST": "BeiJing",
      "L": "BeiJing",
      "O": "k8s",
      "OU": "dyrnq"
    }
  ]
}
EOF
cat > client-csr.json <<EOF
{
  "CN": "client",
  "key": {
    "algo": "rsa",
    "size": 4096
  },
  "names": [
    {
      "O": "tomcat"
    }
  ]
}
EOF

cat server-csr.json;

cfssl gencert -initca ca-csr.json | cfssljson -bare ca
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=config.json -profile=kubernetes server-csr.json | cfssljson -bare server
cfssl gencert -ca=ca.pem -ca-key=ca-key.pem -config=config.json -profile=client client-csr.json | cfssljson -bare client
#openssl pkcs12 -export -out server.p12 -inkey server-key.pem -in server.pem -passout pass:changeit

keytool -importcert -noprompt -trustcacerts -alias server-trust -file ca.pem -keystore truststore.p12 -v -srcstorepass changeit -storepass changeit -storetype PKCS12
keytool -importcert -noprompt -trustcacerts -alias server-trust -file ca.pem -keystore truststore.jks -v -srcstorepass changeit -storepass changeit -storetype JKS



openssl pkcs12 -export -inkey server-key.pem -in server.pem -out server.p12 -chain -CAfile ca.pem -name server -caname ca -passout pass:changeit
openssl pkcs12 -export -inkey client-key.pem -in client.pem -out client.p12 -chain -CAfile ca.pem -name client -caname ca -passout pass:changeit






# Warning:
# The JKS keystore uses a proprietary format. It is recommended to migrate to PKCS12 which is an industry standard format using "keytool -importkeystore -srckeystore identity.jks -destkeystore identity.jks -deststoretype pkcs12".










ls -l .
popd || exit 1
}


fun_run_server(){

## 启动一个  one-way TLS
# https://github.com/Hakky54/mutual-tls-ssl?tab=readme-ov-file#enabling-https-on-the-server-one-way-tls
docker rm -f tls-server >/dev/null 2>&1 || true
docker \
run \
-d \
--restart always \
--name tls-server \
-v $HOME/opt:/host/opt \
-v $HOME/mutual-tls-ssl/server/target/server.jar:/server.jar \
-p 7443:7443 \
eclipse-temurin:21 \
java -jar /server.jar \
--server.port=7443 \
--server.ssl.enabled=true \
--server.ssl.key-store=/host/opt/certs/server.p12 \
--server.ssl.key-store-type=PKCS12 \
--server.ssl.key-password=changeit \
--server.ssl.key-store-password=changeit \
--server.ssl.client-auth=NONE


## 启动一个  two-way TLS
## https://github.com/Hakky54/mutual-tls-ssl?tab=readme-ov-file#require-the-client-to-identify-itself-two-way-tls
docker rm -f mtls-server >/dev/null 2>&1 || true

docker \
run \
-d \
--restart always \
--name mtls-server \
-v $HOME/opt:/host/opt \
-v $HOME/mutual-tls-ssl/server/target/server.jar:/server.jar \
-p 8443:8443 \
eclipse-temurin:21 \
java -jar /server.jar \
--server.port=8443 \
--server.ssl.enabled=true \
--server.ssl.key-store=/host/opt/certs/server.p12 \
--server.ssl.key-store-type=PKCS12 \
--server.ssl.key-password=changeit \
--server.ssl.key-store-password=changeit \
--server.ssl.trust-store=/host/opt/certs/truststore.p12 \
--server.ssl.trust-store-type=PKCS12 \
--server.ssl.trust-store-password=changeit \
--server.ssl.client-auth=NEED


}

fun_test_server(){

pushd $HOME/opt/certs || exit 1
  echo "sleep 10s" && sleep 10s;
  curl https://127.0.0.1:7443/api/hello || true
  fun_line
  curl -k https://127.0.0.1:7443/api/hello || true
  fun_line
  curl --cacert ca.pem https://127.0.0.1:7443/api/hello
  fun_line
  curl https://127.0.0.1:8443/api/hello || true
  fun_line
  curl -k https://127.0.0.1:8443/api/hello || true
  echo "curl: (56) OpenSSL SSL_read: error:14094412:SSL routines:ssl3_read_bytes:sslv3 alert bad certificate, errno 0"
  fun_line
  curl --key client-key.pem --cert client.pem --cacert ca.pem https://127.0.0.1:8443/api/hello
popd || exit 1

}


fun_install_ssl_routes(){



# client_content=$(cat $HOME/opt/certs/client.pem | tr '\n' '\\n')
# client_key_content=$(cat $HOME/opt/certs/client-key.pem | tr '\n' '\\n' )
# ca_content=$(cat $HOME/opt/certs/ca.pem | tr '\n' '\\n' )


## 奇怪 tr 无法把\n替换为\\n ? 使用python3替代
client_content=$(python3 <<EOF
with open('$HOME/opt/certs/client.pem', 'r') as file:
    certificate = file.read().replace('\\n', '\\\n')
print(certificate)
EOF
)
client_key_content=$(python3 <<EOF
with open('$HOME/opt/certs/client-key.pem', 'r') as file:
    certificate = file.read().replace('\\n', '\\\n')
print(certificate)
EOF
)

ca_content=$(python3 <<EOF
with open('$HOME/opt/certs/ca.pem', 'r') as file:
    certificate = file.read().replace('\\n', '\\\n')
print(certificate)
EOF
)



client_content=${client_content%??}
client_key_content=${client_key_content%??}
ca_content=${ca_content%??}
# echo $client_content;
# echo $client_key_content;
# echo $ca_content;


# shellcheck disable=SC2140
# ref https://apisix.apache.org/docs/apisix/tutorials/client-to-apisix-mtls/#configure-the-certificate-in-apisix
curl -X PUT 'http://127.0.0.1:9180/apisix/admin/ssls/50000011' \
--header 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' \
--header 'Content-Type: application/json' \
--data-raw "
{
  \"snis\" : [ \"127.0.0.1\" ],
  \"cert\": \"$client_content\",
  \"key\": \"$client_key_content\",
  \"client\": { \"ca\": \"$ca_content\" },
  \"type\": \"client\"
}
"

sleep 2s;



curl -X PUT 'http://127.0.0.1:9180/apisix/admin/routes/50000010' \
--header 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' \
--header 'Content-Type: application/json' \
--data-raw '
{
  "name": "tls-api",
  "plugins": {
    "proxy-rewrite": {
      "use_real_request_uri_unsafe": false,
      "regex_uri": [
        "^/([^/]+)/(.*)$",
        "/api/${2}"
      ]
    }
  },
  "priority": 10,
  "status": 1,
  "upstream": {
    "hash_on": "vars",
    "nodes": [
      {
        "host": "127.0.0.1", "port": 7443, "weight": 1
      }
    ],
    "pass_host": "pass",
    "scheme": "https",
    "timeout": {
      "connect": 6,
      "send": 6,
      "read": 6
    },
    "type": "roundrobin"
  },
  "uri": "/tls-api/*"
}
'






curl -X PUT 'http://127.0.0.1:9180/apisix/admin/routes/50000011' \
--header 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' \
--header 'Content-Type: application/json' \
--data-raw '
{
  "name": "mtls-api",
  "uri": "/mtls-api/*",
  "plugins": {
    "proxy-rewrite": {
      "headers": {
          "set":{
            "X-Ssl-Client-Fingerprint": "$ssl_client_fingerprint",
            "X-Ssl-Client-Serial": "$ssl_client_serial",
            "X-Ssl-Client-S-DN": "$ssl_client_s_dn"
          }
        },
        "use_real_request_uri_unsafe": false,
        "regex_uri": [
          "^/([^/]+)/(.*)$",
          "/api/${2}"
        ]
    }
  },
  "priority": 10,
  "status": 1,
  "upstream": {
    "scheme": "https",
    "tls": {
      "client_cert_id": "50000011"
    },
    "nodes": [{
      "host": "127.0.0.1",
      "port": 8443,
      "weight": 1
    }],
    "type": "roundrobin"
  }
}
'




}



fun_prepare_env

fun_build_mutual-tls-ssl

if [ ! -f $HOME/opt/certs/server.p12 ]; then
  fun_gen_certs
fi

fun_run_server

fun_test_server

fun_install_ssl_routes

fun_line
curl http://127.0.0.1:9080/tls-api/hello
fun_line
curl http://127.0.0.1:9080/mtls-api/hello
fun_line


