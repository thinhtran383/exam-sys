package space.thinhtran.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {
//    @Bean
//    public OpenAPI userServiceAPI() {
//        return new OpenAPI()
//                .servers(List.of(
//                        new io.swagger.v3.oas.models.servers.Server()
//                                .url("http://localhost:8080")
//                                .description("User Service API")
//                ))
//                .info(new io.swagger.v3.oas.models.info.Info()
//                        .title("User Service API")
//                        .version("1.0.0")
//                        .description("User Service API documentation"));
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gateway Service")
                        .description("API Gateway Service")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .name("bearerAuth")
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
