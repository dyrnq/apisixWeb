#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

set -euo pipefail

curl http://127.0.0.1:9180/apisix/admin/routes/9980 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
  "uri": "/healthz*",
  "name": "healthz",
  "plugins": {
    "mocking": {
      "content_type": "text/html; charset=utf-8",
      "delay": 0,
      "response_example": "ok",
      "response_status": 200,
      "with_mock_header": false
    },
    "response-rewrite": {
      "body_base64": false,
      "headers": {
        "remove": [
          "Server"
        ],
        "set": {
          "server_addr": "$server_addr"
        }
      }
    }
  },
  "status": 1
}
'