#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016

curl http://127.0.0.1:9180/apisix/admin/global_rules/56660 -H 'X-API-KEY: edd1c9f034335f136f87ad84b625c8f1' -X PUT -d '
{
    "plugins": {
        "response-rewrite": {
        "headers": {
                "set":{
                    "hello": "world, i am a global_rules config"
                },
                "remove": ["Server"]
            }
        }
    }
}
'

curl http://127.0.0.1:9080 -I