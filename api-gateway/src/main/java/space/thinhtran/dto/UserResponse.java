package space.thinhtran.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String userId;
    private String accessToken;
    private String refreshToken;
}
