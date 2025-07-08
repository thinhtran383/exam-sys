package space.thinhtran.userservice.dto.request;

import lombok.Data;

@Data
public class CreateUserReq {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
