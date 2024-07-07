package org.devhamzat.authorg.service;

import org.devhamzat.authorg.dto.loginDto.LoginRequestDto;
import org.devhamzat.authorg.dto.loginDto.LoginResponse;
import org.devhamzat.authorg.dto.registrationDto.RegistrationResponse;
import org.devhamzat.authorg.dto.registrationDto.RegistrationRequestDto;

public interface UserService {
    RegistrationResponse registerUser(RegistrationRequestDto registrationRequestDto);

    LoginResponse userLogin(LoginRequestDto loginRequestDto);
}
