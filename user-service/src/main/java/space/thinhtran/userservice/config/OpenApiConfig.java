package space.thinhtran.userservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition
public class OpenApiConfig {
    @Bean
    public OpenAPI userServiceAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new io.swagger.v3.oas.models.servers.Server()
                                .url("http://localhost:8080")
                                .description("User Service API")
                ))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("User Service API")
                        .version("1.0.0")
                        .description("User Service API documentation"));
    }
}
