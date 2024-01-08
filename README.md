This is our ORCiD identity provider for Keycloak. 

## build
### Docker / Podman
```
docker run -it --pull always --rm --name keycloak-orcid-provider -v "$(pwd)":/usr/src/keycloak-orcid-provider -w /usr/src/keycloak-orcid-provider maven:3.9-amazoncorretto-17 mvn clean install
```
