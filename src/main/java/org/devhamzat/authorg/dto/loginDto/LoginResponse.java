package org.devhamzat.authorg.dto.loginDto;

import lombok.Data;
import org.devhamzat.authorg.utils.Status;

@Data
public class LoginResponse {
    private Status status;
    private String message;
    private LoginDataDto data;
}
