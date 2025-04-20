package space.thinhtran.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import space.thinhtran.dto.UserResponse;

@RestController
@Slf4j(topic = "AuthController")
public class AuthController {
    @GetMapping("/authentication/user")
    public ResponseEntity<UserResponse> getUserInfo(
            @RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient,
            @AuthenticationPrincipal OAuth2AuthenticationToken principal
    ) {

        String accessToken = authorizedClient.getAccessToken().getTokenValue();
        String refreshToken = authorizedClient.getRefreshToken().getTokenValue();
        String userId = principal.getPrincipal().getAttribute("sub");

        return ResponseEntity.ok(
                UserResponse.builder()
                        .userId(userId)
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build()
        );
    }
}
