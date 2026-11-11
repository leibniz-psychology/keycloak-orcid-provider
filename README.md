This is our ORCiD identity provider for Keycloak. 

## build
To build, execute the `./build.sh` script. Either Podman or Docker are required.

## Releases
Push a tag in the format `vMAJOR.MINOR.PATCH-MAJOR.MINOR.PATCH`, such as
`v1.4.5-26.8.1`, matching the project version in `pom.xml` (without the `v`).
The Release workflow runs `build.sh` and publishes the built JAR to a GitHub
release named after the tag. Other tag formats are skipped; a version mismatch
fails the workflow without building or publishing.

Tags pushed by another workflow using `GITHUB_TOKEN` do not trigger this workflow.
