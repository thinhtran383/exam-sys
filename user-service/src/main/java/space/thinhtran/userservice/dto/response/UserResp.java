package space.thinhtran.userservice.dto.response;

import lombok.Builder;
import lombok.Data;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@Data
@Builder
public class UserResp {
    private String userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> role;
    private boolean isVerifiedEmail;

    public static UserResp of(UserRepresentation user) {
        if (user == null) {
            return null;
        }

        return UserResp.builder()
                .userId(user.getId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRealmRoles())
                .isVerifiedEmail(user.isEmailVerified())
                .build();
    }
}
