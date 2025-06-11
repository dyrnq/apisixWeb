#!/usr/bin/env bash
set -eo pipefail



_main() {
touch /etc/s6-overlay/s6-rc.d/user/contents.d/apisixweb
exec /init
}

_main "$@"
