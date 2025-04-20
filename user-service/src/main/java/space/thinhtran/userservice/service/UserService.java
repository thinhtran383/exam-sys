package space.thinhtran.userservice.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import space.thinhtran.commonmodule.dto.response.PageResponse;
import space.thinhtran.userservice.config.KeyCloakProperties;
import space.thinhtran.userservice.dto.response.UserResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final Keycloak keycloak;
    private final KeyCloakProperties keyCloakProperties;

    public PageResponse<UserResponse> getAllUsers(int page, int size) {
        List<UserRepresentation> users = keycloak.realm(keyCloakProperties.getRealm())
                .users()
                .list(page * size, size);

        List<UserResponse> userResponses = users.stream()
                .map(user -> UserResponse.builder()
                        .userId(user.getId())
                        .userName(user.getUsername())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build())
                .toList();

        long totalElements = keycloak.realm(keyCloakProperties.getRealm()).users().count();

        int totalPages = (int) Math.ceil((double) totalElements / size);

        return PageResponse.<UserResponse>builder()
                .data(userResponses)
                .page(page)
                .size(size)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasNext(page < totalPages - 1)
                .hasPrevious(page > 0)
                .build();
    }


}
