package org.devhamzat.authorg.dto.loginDto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String password;
}
