This is our ORCiD identity provider for Keycloak.

## build
### Docker
```
docker run -it --rm --name keycloak-orcid-provider -v "$(pwd)":/usr/src/keycloak-orcid-provider -w /usr/src/keycloak-orcid-provider maven:3.8.5-jdk-11 mvn clean install
```
