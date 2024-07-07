package org.devhamzat.authorg.dto.loginDto;

import lombok.Data;
import org.devhamzat.authorg.dto.UserDto;
@Data
public class LoginDataDto {
    private String accessToken;
    private UserDto data;
}
