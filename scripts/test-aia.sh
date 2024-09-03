#!/usr/bin/env bash

test_server="http://127.0.0.1:5980"


server_data=$(curl -s -H "X-API-KEy: your-secret-api-key" ${test_server}/api/v1/config | jq -r ".data" | base64 -d)

echo "$server_data"
#
echo "$server_data" > /tmp/config.yaml
echo "# $(date)你好"  >> /tmp/config.yaml
base64Str=$(base64 --wrap=0 /tmp/config.yaml)
echo ""
echo $base64Str
#
curl -s -H "X-API-KEy: your-secret-api-key" -X POST ${test_server}/api/v1/config -d "{\"data\": \"$base64Str\"}"


curl -s -H "X-API-KEy: your-secret-api-key" -X GET ${test_server}/api/v1/reload | jq
sleep 4s;
curl -s -H "X-API-KEy: your-secret-api-key" -X GET ${test_server}/api/v1/restart | jq
sleep 4s;
curl -s -H "X-API-KEy: your-secret-api-key" -X GET ${test_server}/api/v1/stop | jq
sleep 4s;
curl -s -H "X-API-KEy: your-secret-api-key" -X GET ${test_server}/api/v1/start | jq
