package dev.minn_shop.minn_shop.security.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationRequest {
    @NotNull(message="Username is mandatory!")
    @NotBlank(message="Username is mandatory!")
    @Size(min = 6, max = 20, message = "Username must be at least 6 character")
    private String username;

    @NotNull(message="Password is mandatory!")
    @NotBlank(message="Password is mandatory!")
    @Size(min = 6, max = 20, message = "Password must be at least 6 character")
    private String password;
    
    @NotNull(message="First name is mandatory!")
    @NotBlank(message="First name is mandatory!")
    @Size(min = 2, max = 10, message = "First name must be 2-10 character")
    private String firstName;

    @NotNull(message="Last name is mandatory!")
    @NotBlank(message="Last name is mandatory!")
    @Size(min = 2, max = 10, message = "Last name must be 2-10 character")
    private String lastName;

    @NotNull(message="Phone number is mandatory!")
    @NotBlank(message="Phone number is mandatory!")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$")
    private String phoneNumber;
    
    private String role;
}
