package dev.minn_shop.minn_shop.user;

import java.util.Date;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DetailUserRecord(
        @NotNull Integer id,

        @NotNull(message = "Username is mandatory!") 
        @NotBlank(message = "Username is mandatory!") 
        @Size(min = 6, max = 20, message = "Username must be at least 6 character") 
        String username,

        @NotNull(message = "First name is mandatory!") 
        @NotBlank(message = "First name is mandatory!") 
        @Size(min = 2, max = 10, message = "First name must be 2-10 character") 
        String firstName,

        @NotNull(message = "Last name is mandatory!") 
        @NotBlank(message = "Last name is mandatory!") 
        @Size(min = 2, max = 10, message = "Last name must be 2-10 character") 
        String lastName,

        @Email(message="Email is wrong formated")
        String email,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message="Phone number is wrong formated")
        String phoneNumber,

        Date birthDate,

        byte[] avatar) {
                public String fullName(){
                        return firstName + " " + lastName;
                    } 
}
