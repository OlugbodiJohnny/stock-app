package com.example.stockapp2.services.impl;

import com.example.stockapp2.constants.AuthoritiesConstants;
import com.example.stockapp2.dto.request.LoginReq;
import com.example.stockapp2.dto.request.RegisterUserReq;
import com.example.stockapp2.dto.response.HttpResponseDto;
import com.example.stockapp2.dto.response.JwtResponse;
import com.example.stockapp2.exceptions.ApiBadRequestException;
import com.example.stockapp2.models.Authority;
import com.example.stockapp2.models.User;
import com.example.stockapp2.repositories.AuthorityRepository;
import com.example.stockapp2.repositories.UserRepository;
import com.example.stockapp2.security.jwt.JwtUtils;
import com.example.stockapp2.security.service.UserDetailsImpl;
import com.example.stockapp2.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleAssignment roleAssignment;
    private final AuthorityRepository roleRepository;
    private final String ENTITY = "USER";

    @Override
    public ResponseEntity<?> registerUser(RegisterUserReq registerUserReq) {
        log.info("full name is : {}", registerUserReq.getFullName());
        if (registerUserReq.getFullName().split(" ").length < 2)
            throw new ApiBadRequestException("Please provide first and last name seperated by a space");
        User user = new User();
        user.setEmail(registerUserReq.getEmail().toLowerCase());
        user.setPassword(passwordEncoder.encode(registerUserReq.getPassword()));
        user.setFullName(registerUserReq.getFullName());
        Set<String> stringList = new HashSet<>(List.of(AuthoritiesConstants.USER));
        Set<Authority> roleList = roleAssignment.assignRole(stringList, roleRepository);
        user.setAuthorities(roleList);
        User savedUser = userRepository.save(user);
        log.info("user was registered");
        //send notification to email
        //generating otp
        return new ResponseEntity<>(new HttpResponseDto(HttpStatus.OK,ENTITY,"Registration was successful",savedUser),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> login (LoginReq loginReq) {
        loginReq.setEmail(loginReq.getEmail().toLowerCase());
        log.info("authenticating user");
        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
        }catch (BadCredentialsException e) {
            throw new ApiBadRequestException("Invalid credentials");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        log.info("user authenticated");
        log.info("returning  token");
        return new ResponseEntity<>(new HttpResponseDto(HttpStatus.OK,ENTITY,"Login was successful",new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles)),HttpStatus.OK);
    }
}
