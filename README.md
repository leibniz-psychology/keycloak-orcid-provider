This is our ORCiD identity provider for Keycloak which also imports users' email addresses into Keycloak for further use in our applications.

## notes
### refreshing e-mail addresses
Ideally, imported e-mail addresses should be re-fetched on every login. This way, the addresses would be updated when users update their e-mail in ORCiD.
However, for unknown reasons, this only works in the ORCiD sandbox.

## build
### Docker
```
docker run -it --rm --name keycloak-orcid-provider -v "$(pwd)":/usr/src/keycloak-orcid-provider -w /usr/src/keycloak-orcid-provider maven:3.8.5-jdk-11 mvn clean install
```
