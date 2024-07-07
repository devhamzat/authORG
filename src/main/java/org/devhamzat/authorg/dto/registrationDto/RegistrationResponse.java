package org.devhamzat.authorg.dto.registrationDto;

import lombok.Data;
import org.devhamzat.authorg.utils.Status;

@Data
public class RegistrationResponse {
    private Status status;
    private String message;
    private RegistrationDataDto data;
}
