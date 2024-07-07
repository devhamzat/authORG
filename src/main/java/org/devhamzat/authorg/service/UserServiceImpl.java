package org.devhamzat.authorg.service;

import org.devhamzat.authorg.dto.*;
import org.devhamzat.authorg.dto.loginDto.LoginDataDto;
import org.devhamzat.authorg.dto.loginDto.LoginRequestDto;
import org.devhamzat.authorg.dto.loginDto.LoginResponse;
import org.devhamzat.authorg.dto.registrationDto.RegistrationDataDto;
import org.devhamzat.authorg.dto.registrationDto.RegistrationRequestDto;
import org.devhamzat.authorg.dto.registrationDto.RegistrationResponse;
import org.devhamzat.authorg.entity.Organization;
import org.devhamzat.authorg.entity.User;
import org.devhamzat.authorg.exception.RegistrationException;
import org.devhamzat.authorg.repository.OrganizationRepository;
import org.devhamzat.authorg.repository.UserRepository;
import org.devhamzat.authorg.utils.OrganizationIdGenerator;
import org.devhamzat.authorg.utils.Status;
import org.devhamzat.authorg.utils.UserIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    //    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private OrganizationIdGenerator organizationIdGenerator;
    @Autowired
    private UserIdGenerator userIdGenerator;

    RegistrationDataDto registrationDataDto = new RegistrationDataDto();

    public UserServiceImpl(UserRepository userRepository, OrganizationIdGenerator organizationIdGenerator, UserIdGenerator userIdGenerator) {
        this.userRepository = userRepository;
        this.organizationIdGenerator = organizationIdGenerator;
        this.userIdGenerator = userIdGenerator;
    }

    //Todo: hash password
    @Override
    public RegistrationResponse registerUser(RegistrationRequestDto registrationRequestDto) {
        if (userRepository.existsByEmail(registrationRequestDto.getEmail())) {
            throw new RegistrationException("Email is taken", 409);
        }
        if (registrationRequestDto.getEmail().isEmpty() ||
                registrationRequestDto.getLastName().isEmpty() ||
                registrationRequestDto.getPhone().isEmpty() ||
                registrationRequestDto.getPassword().isEmpty() ||
                registrationRequestDto.getFirstName().isEmpty()
        ) {
            throw new RegistrationException("Registration unsuccessful", 400);
        }
        User user = new User();
        user.setUserId(userIdGenerator.generateUserId());
        user.setFirstName(registrationRequestDto.getFirstName());
        user.setLastName(registrationRequestDto.getLastName());
        user.setEmail(registrationRequestDto.getEmail());
        user.setPhone(registrationRequestDto.getPhone());
        user.setPassword(registrationRequestDto.getPassword());

        Organization organization = new Organization();
        organization.setOrgId(organizationIdGenerator.generateOrganizationId());
        organization.setName(user.getFirstName() + "'s organisation");
        registrationDataDto.setAccessToken("hi");
        UserDataDtoMapper userDataDtoMapper = new UserDataDtoMapper();
        registrationDataDto.setData(userDataDtoMapper.mapUserToDto(user));
        RegistrationResponse registrationResponse = new RegistrationResponse();
        registrationResponse.setStatus(Status.success);
        registrationResponse.setMessage("Registration successful");
        registrationResponse.setData(registrationDataDto);

//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        organizationRepository.save(organization);
        return registrationResponse;
    }

    @Override
    public LoginResponse userLogin(LoginRequestDto loginRequestDto) {
        Optional<User> userInfo = userRepository.findByEmail(loginRequestDto.getEmail());
        if (userInfo.isPresent()) {
            if (userInfo.get().getEmail().equals(loginRequestDto.getEmail()) && userInfo.get().getPassword().equals(loginRequestDto.getPassword())) {
                UserDto userDto = new UserDto();
                userDto.setUserId(userInfo.get().getUserId());
                userDto.setFirstName(userInfo.get().getFirstName());
                userDto.setLastName(userInfo.get().getLastName());
                userDto.setEmail(userInfo.get().getEmail());
                userDto.setPhone(userInfo.get().getPhone());

                LoginDataDto loginDataDto = new LoginDataDto();
                loginDataDto.setAccessToken("hello");
                loginDataDto.setData(userDto);

                LoginResponse loginResponse = new LoginResponse();
                loginResponse.setStatus(Status.success);
                loginResponse.setMessage("Login successful");
                loginResponse.setData(loginDataDto);
                return loginResponse;
            }


        }
        throw new RegistrationException("Authentication failed", 401);
    }
}
