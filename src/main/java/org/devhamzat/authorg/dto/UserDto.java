package org.devhamzat.authorg.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    @NotNull
    private String userId;
    @NotNull
    private String firstName;
    @NotNull(message = "email can not be email")
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String phone;
}
