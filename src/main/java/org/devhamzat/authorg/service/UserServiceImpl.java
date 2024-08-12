package org.devhamzat.authorg.service;

import org.devhamzat.authorg.dto.UserDataDtoMapper;
import org.devhamzat.authorg.dto.UserDto;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    OrganizationIdGenerator organizationIdGenerator;
    @Autowired
    UserIdGenerator userIdGenerator;

//    private JwtTokenHandler jwtTokenHandler;


    RegistrationDataDto registrationDataDto = new RegistrationDataDto();

    public UserServiceImpl(UserRepository userRepository, UserIdGenerator userIdGenerator, OrganizationIdGenerator organizationIdGenerator, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager
//                           JwtTokenHandler jwtTokenHandler
    ) {
        this.userRepository = userRepository;
        this.userIdGenerator = userIdGenerator;
        this.organizationIdGenerator = organizationIdGenerator;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
//        this.jwtTokenHandler = jwtTokenHandler;
    }

    public UserServiceImpl() {

    }

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

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        organizationRepository.save(organization);
        return registrationResponse;
    }


    public LoginResponse userLogin(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Optional<User> userInfo = userRepository.findByEmail(loginRequestDto.getEmail());
        UserDto userDto = new UserDto();
        userDto.setUserId(userInfo.get().getUserId());
        userDto.setFirstName(userInfo.get().getFirstName());
        userDto.setLastName(userInfo.get().getLastName());
        userDto.setEmail(userInfo.get().getEmail());
        userDto.setPhone(userInfo.get().getPhone());
//        String token = jwtTokenHandler.generateToken(authentication);
        LoginDataDto loginDataDto = new LoginDataDto();
        loginDataDto.setAccessToken("");
        loginDataDto.setData(userDto);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setStatus(Status.success);
        loginResponse.setMessage("Login successful");
        loginResponse.setData(loginDataDto);
        return loginResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        Set<GrantedAuthority> authorities = user.get()
                .getOrganization()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(),
                user.get().getPassword(),
                authorities);
    }
}
