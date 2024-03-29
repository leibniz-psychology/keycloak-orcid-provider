/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.social.orcid;

import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.social.paypal.PayPalIdentityProviderConfig;
import java.util.Map;
import org.keycloak.provider.ServerInfoAwareProviderFactory;

/**
 * @author Marc Schulz-Narres
 */
public class ORCIDIdentityProviderFactory extends AbstractIdentityProviderFactory<ORCIDIdentityProvider> implements SocialIdentityProviderFactory<ORCIDIdentityProvider>, ServerInfoAwareProviderFactory {

    public static final String PROVIDER_ID = "orcid";

    @Override
    public String getName() {
        return "ORCID";
    }

    @Override
    public ORCIDIdentityProvider create(KeycloakSession session, IdentityProviderModel model) {
        return new ORCIDIdentityProvider(session, new ORCIDIdentityProviderConfig(model));
    }

    @Override
    public ORCIDIdentityProviderConfig createConfig() {
        return new ORCIDIdentityProviderConfig();
    }


    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    /**
     * Gladly copied from https://github.com/sventorben/keycloak-home-idp-discovery/blob/7c5691000afc95db1a3a5527e296c50aa79c08e3/src/main/java/de/sventorben/keycloak/authentication/hidpd/HomeIdpDiscoveryAuthenticatorFactory.java#L86C1-L94C6
     * @author Manuel Biertz
     */
    @Override
    public Map<String, String> getOperationalInfo() {
        String version = getClass().getPackage().getImplementationVersion();
        if (version == null) {
            version = "dev-snapshot";
        }
        return Map.of("Version", version);
    }
}
