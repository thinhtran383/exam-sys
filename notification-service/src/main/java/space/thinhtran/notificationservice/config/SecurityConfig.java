package space.thinhtran.notificationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import space.thinhtran.commonmodule.config.KeycloakRoleConverter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final KeycloakRoleConverter keycloakRoleConverter;

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(keycloakRoleConverter);
        return converter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/noti/**").permitAll()

                        .requestMatchers(HttpMethod.GET,
                                "/actuator/**",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/webjars/**",
                                "/eureka/web/**",
                                "/eureka/**",
                                "/aggregate/**",
                                "/api-docs",
                                "/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth -> oauth.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
                ));
        return http.build();
    }


}
