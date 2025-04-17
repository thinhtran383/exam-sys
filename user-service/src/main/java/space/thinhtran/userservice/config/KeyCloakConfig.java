package space.thinhtran.userservice.config;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.keycloak.OAuth2Constants.CLIENT_CREDENTIALS;
import static org.keycloak.OAuth2Constants.PASSWORD;

@Configuration
@RequiredArgsConstructor
public class KeyCloakConfig {
    private final KeyCloakProperties keyCloakProperties;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(keyCloakProperties.getServerUrl())
                .realm(keyCloakProperties.getRealm())
                .grantType(PASSWORD)
                .username(keyCloakProperties.getUsername())
                .password(keyCloakProperties.getPassword())
                .clientId(keyCloakProperties.getClientId())
//                .clientSecret(keyCloakProperties.getClientSecret())
                .build();
    }

}
