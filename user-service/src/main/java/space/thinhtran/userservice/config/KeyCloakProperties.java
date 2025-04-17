package space.thinhtran.userservice.config;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@Slf4j(topic = "KeyCloakProperties")
@ToString(exclude = {"password"})
@ConfigurationProperties(prefix = "keycloak.admin")
public class KeyCloakProperties {
    private String serverUrl;
    private String realm;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;

    @Bean
    public String log() {
        log.info("KeyCloakProperties: {}", this);
        return null;
    }
}
