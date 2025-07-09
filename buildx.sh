#!/usr/bin/env bash
set -Eeo pipefail

base_image="${base_image:-}"
version="${version:-}";
push="${push:-false}"
repo="${repo:-dyrnq}"
image_name="${image_name:-apisixweb}"
platforms="${platforms:-linux/amd64,linux/arm64/v8}"
curl_opts="${curl_opts:-}"
S6_OVERLAY_VERSION=3.2.0.2

while [ $# -gt 0 ]; do
    case "$1" in
        --base-image|--base)
            base_image="$2"
            shift
            ;;
        --version|--ver)
            version="$2"
            shift
            ;;
        --push)
            push="$2"
            shift
            ;;
        --curl-opts)
            curl_opts="$2"
            shift
            ;;
        --platforms)
            platforms="$2"
            shift
            ;;
        --repo)
            repo="$2"
            shift
            ;;
        --image-name|--image)
            image_name="$2"
            shift
            ;;
        --*)
            echo "Illegal option $1"
            ;;
    esac
    shift $(( $# > 0 ? 1 : 0 ))
done



# # https://docs.docker.com/build/building/multi-platform/
# docker run --privileged --rm tonistiigi/binfmt --install all
# # https://github.com/docker/buildx/issues/208
# docker run --privileged --rm multiarch/qemu-user-static --reset -p yes
# docker buildx create --name mbuilder --use 2>/dev/null || docker buildx use mbuilder
# docker buildx inspect --bootstrap

chmod +x ./docker/rootfs/docker-entrypoint.sh
chmod +x ./docker/rootfs/etc/s6-overlay/s6-rc.d/apisixweb/run



line="${base_image}"

echo "base_image=${base_image}"
#image=$(awk -F: '{print $1}' <<< "${line}")
tag=$(awk -F: '{print $2}' <<< "${line}")
dockerfile="${line}_Dockerfile"


short_sha=$(git rev-parse --short=7 HEAD)
echo "$short_sha"



latest_tag="";

if [ "$tag" = "21-jre-noble"  ]; then
    latest_tag="--tag $repo/$image_name:latest"
    latest_tag="${latest_tag} --tag $repo/$image_name:latest-jre21"
    latest_tag="${latest_tag} --tag $repo/$image_name:git-${short_sha}-jre21"
elif [ "$tag" = "11-jre-noble"  ]; then
    latest_tag="--tag $repo/$image_name:latest-jre11"
    latest_tag="${latest_tag} --tag $repo/$image_name:git-${short_sha}-jre11"
elif [ "$tag" = "8-jre-noble"  ]; then
    latest_tag="--tag $repo/$image_name:latest-jre8"
    latest_tag="${latest_tag} --tag $repo/$image_name:git-${short_sha}-jre8"
elif [ "$tag" = "21-jdk-noble"  ]; then
    latest_tag="--tag $repo/$image_name:latest-jdk21"
    latest_tag="${latest_tag} --tag $repo/$image_name:git-${short_sha}-jdk21"
elif [ "$tag" = "11-jdk-noble"  ]; then
    latest_tag="--tag $repo/$image_name:latest-jdk11"
    latest_tag="${latest_tag} --tag $repo/$image_name:git-${short_sha}-jdk11"
elif [ "$tag" = "8-jdk-noble"  ]; then
    latest_tag="--tag $repo/$image_name:latest-jdk8"
    latest_tag="${latest_tag} --tag $repo/$image_name:git-${short_sha}-jdk8"
fi

latest_tag="${latest_tag} --tag $repo/$image_name:latest-${tag}"
latest_tag="${latest_tag} --tag $repo/$image_name:git-${short_sha}-${tag}"

echo "${latest_tag}"
sed -e "s@__BASE_IMAGE__@$line@g" ./docker/Dockerfile > ./docker/${dockerfile}


docker buildx build \
--platform ${platforms} \
--output "type=image,push=$push" \
--file ./docker/${dockerfile} . \
--build-arg S6_OVERLAY_VERSION="${S6_OVERLAY_VERSION}" \
${latest_tag}

rm -rf ./docker/${dockerfile}
