# Development Guidelines for Exam System

This document provides guidelines and information for developers working on the Exam System project. It includes build/configuration instructions, testing information, and additional development details.

## Project Structure

The Exam System is a microservices-based application built with Spring Boot and Spring Cloud. It consists of the following modules:

- **api-gateway**: API Gateway service that routes requests to appropriate microservices
- **discovery-server**: Service registry using Netflix Eureka
- **user-service**: User management service integrated with Keycloak
- **common-module**: Shared utilities and DTOs used across services

## Build and Configuration Instructions

### Prerequisites

- Java 17
- Maven 3.8+
- Keycloak 26.0.1+ running on port 8181

### Building the Project

1. Clone the repository
2. Build the entire project:
   ```bash
   mvn clean install
   ```
3. To build a specific module:
   ```bash
   mvn clean install -pl <module-name>
   ```

### Configuration

#### Keycloak Configuration

The project uses Keycloak for authentication and authorization. You need to:

1. Set up a Keycloak server running on port 8181
2. Create a realm named `ms-demo-realm`
3. Configure client credentials as specified in the `application.properties` files

Key Keycloak properties in `user-service/src/main/resources/application.properties`:
```properties
keycloak.admin.username=thinhtran383
keycloak.admin.password=Thinh@123
keycloak.admin.server-url=http://localhost:8181
keycloak.admin.realm=ms-demo-realm
keycloak.admin.client-id=admin-cli
keycloak.admin.client-secret=ZbVfKyoWs4kFY6Fsn2QfSkjc8oYRqTY2
```

#### Service Configuration

Each service has its own `application.properties` file with service-specific configurations:

- **discovery-server**: Runs on port 8761
- **api-gateway**: Configuration for routing and security
- **user-service**: Runs on port 8282, connects to Keycloak and Eureka

## Testing Information

### Running Tests

The project uses JUnit 5 and Mockito for testing. To run tests:

1. Run all tests in the project:
   ```bash
   mvn test
   ```

2. Run tests for a specific module:
   ```bash
   mvn test -pl <module-name>
   ```

3. Run a specific test class:
   ```bash
   mvn test -pl <module-name> -Dtest=<TestClassName>
   ```

4. Run a specific test method:
   ```bash
   mvn test -pl <module-name> -Dtest=<TestClassName>#<methodName>
   ```

### Writing Tests

#### Service Layer Tests

For service layer tests, use Mockito to mock dependencies. Here's an example of testing a service that depends on Keycloak:

```java
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private Keycloak keycloak;

    @Mock
    private KeyCloakProperties keyCloakProperties;

    @Mock
    private RealmResource realmResource;

    @Mock
    private UsersResource usersResource;

    @InjectMocks
    private UserService userService;

    private final String REALM_NAME = "test-realm";

    @BeforeEach
    void setUp() {
        when(keyCloakProperties.getRealm()).thenReturn(REALM_NAME);
        when(keycloak.realm(REALM_NAME)).thenReturn(realmResource);
        when(realmResource.users()).thenReturn(usersResource);
    }

    @Test
    void getAllUsers_ShouldReturnUsersList() {
        // Arrange
        List<UserRepresentation> mockUsers = Arrays.asList(
            createUserRepresentation("user1", "User One"),
            createUserRepresentation("user2", "User Two")
        );
        
        when(usersResource.list()).thenReturn(mockUsers);

        // Act
        PageResponse<UserRepresentation> result = userService.getAllUsers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getData().size());
    }
}
```

#### Controller Layer Tests

For controller layer tests, use MockMvc to test REST endpoints:

```java
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers_ShouldReturnUsers() throws Exception {
        // Arrange
        PageResponse<UserRepresentation> mockResponse = createMockPageResponse();
        when(userService.getAllUsers()).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.totalElements").value(2));
    }
}
```

## Additional Development Information

### Code Style and Conventions

- The project uses Lombok to reduce boilerplate code. Annotations like `@Data`, `@Builder`, and `@RequiredArgsConstructor` are commonly used.
- DTOs are used for data transfer between services and for API responses.
- Common response formats are defined in the `common-module`:
  - `ApiResponse`: For general API responses
  - `PageResponse`: For paginated data

### API Documentation

The project uses SpringDoc OpenAPI for API documentation:

- Swagger UI is available at: `http://<service-host>:<service-port>/swagger-ui.html`
- OpenAPI JSON is available at: `http://<service-host>:<service-port>/api-docs`

### Security

- The project uses OAuth2 with Keycloak for authentication and authorization
- JWT tokens are used for securing API endpoints
- The API Gateway handles authentication and forwards requests to microservices

### Troubleshooting

- If you encounter Keycloak connection issues, ensure the Keycloak server is running and the configuration in `application.properties` is correct.
- For service discovery issues, check that the Eureka server is running and services are properly registered.
- When adding new dependencies, ensure they are compatible with the existing Spring Boot and Spring Cloud versions.