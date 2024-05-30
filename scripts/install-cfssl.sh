#!/usr/bin/env bash
# shellcheck disable=SC2086

# https://github.com/cloudflare/cfssl


ver=${ver:-1.6.5}
target=${target:-/usr/local/bin/}
mirror=${mirror:-}
baseurl="https://github.com"
https_proxy=""
array=(cfssl cfssljson cfssl-bundle cfssl-certinfo cfssl-newkey cfssl-scan mkbundle multirootca)


while [ $# -gt 0 ]; do
    case "$1" in
        --proxy)
            https_proxy="$2"
            shift
            ;;
        --mirror|-m)
            mirror="$2"
            shift
            ;;
        --target|-d)
            target="$2"
            shift
            ;;
        --ver|--version)
            ver="$2"
            shift
            ;;
        --*)
            echo "Illegal option $1"
            ;;
    esac
    shift $(( $# > 0 ? 1 : 0 ))
done

fun_install(){
if [[ $target == */ ]]; then
    target=${target%?}
fi
echo "$target"


if [ "${mirror}" = "daocloud" ] ; then
    baseurl="https://files.m.daocloud.io/github.com"
elif [ "${mirror}" = "ghproxy" ]; then
    baseurl="https://mirror.ghproxy.com/github.com"
fi

if [ ! "${https_proxy}X" = "X" ] ; then
    curl_opts="-x ${https_proxy}"
fi




for i in "${array[@]}"; do
outfile="${target}/${i}"
url=${baseurl}/cloudflare/cfssl/releases/download/v${ver}/${i}_${ver}_linux_amd64
echo "download ${outfile} from $url" && \
curl ${curl_opts} -fSL -# --retry 100 -o ${outfile} ${url}
done

for i in "${array[@]}"; do
chmod +x --verbose ${target}/${i};
done
}


if [ -w "${target}" ]; then
  :
else
  (
    echo "Error!"
    echo "The current user does not have write permission \"${target}\" !"
    echo "Please pass -d change target dir"
    echo "Or change user and re install this script"
    echo "Exit install."
  ) && exit 1
fi


fun_install