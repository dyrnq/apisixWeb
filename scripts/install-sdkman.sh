#!/usr/bin/env bash
# shellcheck disable=SC2086
# shellcheck disable=SC2016
# shellcheck disable=SC1091


sudo apt install zip unzip curl wget -y



curl -fsSL --retry 100 -o /tmp/install-sdkman.sh "https://get.sdkman.io" && \
chmod +x /tmp/install-sdkman.sh && \
/tmp/install-sdkman.sh






source "$HOME/.sdkman/bin/sdkman-init.sh"

sdk install java 21.0.3-amzn
sdk install maven 3.9.6