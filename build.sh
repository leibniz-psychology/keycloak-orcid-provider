DOCKER_BIN=/usr/bin/docker
PODMAN_BIN=/usr/bin/podman

if ( which podman > /dev/null 2>&1 ); then
        OCI_RUNTIME_BIN=$PODMAN_BIN
elif ( which docker > /dev/null 2>&1 ); then
        echo "Podman unavailable, defaulting to Docker. Please be aware that Docker supports less features than Podman, thus your experience might get a little quirky."
        OCI_RUNTIME_BIN=$DOCKER_BIN
else
        echo "Neither Podman nor Docker available. Exiting."
        exit;
fi

build () {
        if [ $OCI_RUNTIME_BIN == $PODMAN_BIN ]; then
		$OCI_RUNTIME_BIN run -it --name keycloak-orcid-provider --pull always --rm --security-opt label=disable -v "$(pwd)":/usr/src/keycloak-orcid-provider -w /usr/src/keycloak-orcid-provider maven:3.9-amazoncorretto-17 mvn clean install
        else
		$OCI_RUNTIME_BIN run -it --name keycloak-orcid-provider --pull always --rm -v "$(pwd)":/usr/src/keycloak-orcid-provider -w /usr/src/keycloak-orcid-provider maven:3.9-amazoncorretto-17 mvn clean install
        fi
}

build
