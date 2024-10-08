package org.devhamzat.authorg.controller;

import org.devhamzat.authorg.dto.loginDto.LoginRequestDto;
import org.devhamzat.authorg.dto.loginDto.LoginResponse;
import org.devhamzat.authorg.dto.registrationDto.RegistrationResponse;
import org.devhamzat.authorg.dto.registrationDto.RegistrationRequestDto;
import org.devhamzat.authorg.entity.User;
import org.devhamzat.authorg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody RegistrationRequestDto registrationRequestDto) {
        RegistrationResponse registrationResponse = userService.registerUser(registrationRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationResponse);
    }

    @PostMapping("/log-in")

    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponse loginResponse = userService.userLogin(loginRequestDto);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);


    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";

    }
}
