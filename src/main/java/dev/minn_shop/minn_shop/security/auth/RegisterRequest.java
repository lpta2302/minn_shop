package dev.minn_shop.minn_shop.security.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 6, max = 20, message = "Username must be at least 6 character")
    private String username;
    @Size(min = 6, max = 20, message = "Password must be at least 6 character")
    private String password;
    @Size(min = 2, max = 10, message = "First name must be 2-10 character")
    private String firstName;
    @Size(min = 2, max = 10, message = "Last name must be 2-10 character")
    private String lastName;
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phoneNumber;
    private String role;
}
