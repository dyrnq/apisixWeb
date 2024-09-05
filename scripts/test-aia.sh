#!/usr/bin/env bash

set -eu -o pipefail

test_server="http://127.0.0.1:5980"
apiKey="your-secret-api-key"

server_data=$(curl --fail -sS -H "X-API-KEy: ${apiKey}" ${test_server}/api/v1/config | jq -r ".data" | base64 -d)

echo "$server_data"
#
tempfile=$(mktemp -t config_XXXX.yaml)


echo "$server_data" > "${tempfile}"
echo "# $(date)你好"  >> "${tempfile}"
base64Str=$(base64 --wrap=0 ${tempfile})
echo ""
echo $base64Str
#
curl --fail -sS -H "X-API-KEy: ${apiKey}" -X POST ${test_server}/api/v1/config -d "{\"data\": \"$base64Str\"}"


curl --fail -sS -H "X-API-KEy: ${apiKey}" -X GET ${test_server}/api/v1/reload | jq
sleep 4s;
curl --fail -sS -H "X-API-KEy: ${apiKey}" -X GET ${test_server}/api/v1/restart | jq
sleep 4s;
curl --fail -sS -H "X-API-KEy: ${apiKey}" -X GET ${test_server}/api/v1/stop | jq
sleep 4s;
curl --fail -sS -H "X-API-KEy: ${apiKey}" -X GET ${test_server}/api/v1/start | jq
