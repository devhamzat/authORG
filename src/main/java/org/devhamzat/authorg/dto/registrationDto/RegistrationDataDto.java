package org.devhamzat.authorg.dto.registrationDto;

import lombok.Data;
import org.devhamzat.authorg.dto.UserDto;

@Data
public class RegistrationDataDto {
    private String accessToken;
    private UserDto data;
}
