package space.thinhtran.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import space.thinhtran.commonmodule.config.KeycloakRoleConverter;

@EnableWebFluxSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final KeycloakRoleConverter keycloakRoleConverter;
    private final ReactiveClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }

    private ServerLogoutSuccessHandler logoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler oidcLogoutSuccessHandler =
                new OidcClientInitiatedServerLogoutSuccessHandler(this.clientRegistrationRepository);
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
//        RedirectServerAuthenticationSuccessHandler successHandler = new RedirectServerAuthenticationSuccessHandler("/authentication/user");


        jwtAuthenticationConverter().setJwtGrantedAuthoritiesConverter(keycloakRoleConverter);

        httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
//                .oauth2Login(oauth2 -> oauth2.authenticationSuccessHandler(successHandler))
//                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .logout(logout -> logout.logoutSuccessHandler(logoutSuccessHandler()))

                .authorizeExchange(exchange -> exchange
                                .pathMatchers(
                                        "/actuator/**",
                                        "/eureka/web/**",
                                        "/eureka/**",
                                        "/swagger-ui.html",
                                        "/swagger-ui/**",
                                        "/webjars/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/aggregate/**",
                                        "/webjars/**",
                                        "/logout",
                                        "/login/**"
//                                "/ws/notify/**"
                                ).permitAll()

                                .pathMatchers("/authentication/user").authenticated()

                                .anyExchange().authenticated()

                )
                .oauth2ResourceServer(oauth ->
                        oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(
                                new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter())
                        )));

        return httpSecurity.build();
    }
}
