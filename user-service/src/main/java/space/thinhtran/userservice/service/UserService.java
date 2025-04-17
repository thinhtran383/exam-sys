package space.thinhtran.userservice.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.springframework.stereotype.Service;
import space.thinhtran.userservice.config.KeyCloakConfig;
import space.thinhtran.userservice.config.KeyCloakProperties;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Keycloak keycloak;
    private final KeyCloakProperties keyCloakProperties;

    public List<?> getAllUsers() {
        return keycloak.realm(keyCloakProperties.getRealm()).users().list();
    }

}
