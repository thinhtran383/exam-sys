package space.thinhtran.userservice.service;

import jakarta.ws.rs.core.Response;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import space.thinhtran.commonmodule.dto.response.PageResponse;
import space.thinhtran.userservice.config.KeyCloakProperties;
import space.thinhtran.userservice.dto.request.CreateUserReq;
import space.thinhtran.userservice.dto.response.UserResp;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j(topic = "UserService")
public class UserService {

    private final Keycloak keycloak;
    private final KeyCloakProperties keyCloakProperties;
    private final String REALM_NAME = keyCloakProperties.getRealm();

    public PageResponse<UserResp> getAllUsers(int page, int size) {
        List<UserRepresentation> users = keycloak.realm(REALM_NAME)
                .users()
                .list(page * size, size);

        log.info("UserRepresentation: {}", users.toString());

        List<UserResp> userRespons = users.stream()
                .map(UserResp::of)
                .toList();

        long totalElements = keycloak.realm(REALM_NAME).users().count();

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<UserResp>builder()
                .data(userRespons)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasNext(page < totalPages - 1)
                .hasPrevious(page > 0)
                .build();
    }

    public UserResp createUser(CreateUserReq createUserReq) {

        UserRepresentation newUser = new UserRepresentation();

        newUser.setUsername(createUserReq.getUsername());
        newUser.setEmail(createUserReq.getEmail());
        newUser.setFirstName(createUserReq.getFirstName());
        newUser.setLastName(createUserReq.getLastName());
        newUser.setEnabled(true);
        newUser.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();



        @Cleanup Response response = keycloak.realm(REALM_NAME).users().create(newUser);

        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = keycloak.realm(REALM_NAME).users().get(userId);
        RoleRepresentation guestRealmRole =  keycloak.realm(REALM_NAME).roles().get("ADMIN").toRepresentation();

        userResource.roles().realmLevel().add(Collections.singletonList(guestRealmRole));


        return UserResp.of(newUser);
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }


}
