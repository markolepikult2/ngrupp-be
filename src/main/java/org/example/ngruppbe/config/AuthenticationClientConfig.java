package org.example.ngruppbe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
public class AuthenticationClientConfig {
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration.withRegistrationId("ngrupp-client")
            .clientId("ngrupp-client-id")
            .clientSecret("ngrupp-client-secret")
            .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE)
            .redirectUri("http://localhost:8080/login/oauth2/code/ngrupp-client")
            .scope("event:read")
            .authorizationUri("http://localhost:8080/oauth2/authorize")
            .tokenUri("http://localhost:8080/oauth2/token")
            .build();
        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
}

