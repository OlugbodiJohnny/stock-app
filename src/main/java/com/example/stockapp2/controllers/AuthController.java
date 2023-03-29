package com.example.stockapp2.controllers;

import com.example.stockapp2.dto.request.LoginReq;
import com.example.stockapp2.dto.request.RegisterUserReq;
import com.example.stockapp2.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

//    @SecurityRequirement(name = "jwt")
    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser (@RequestBody RegisterUserReq registerUserReq) {
        return userService.registerUser(registerUserReq);
    }

//    @SecurityRequirement(name = "jwt")
    @PostMapping("/auth/login")
    public ResponseEntity<?> login (@RequestBody LoginReq loginReq) {
        return userService.login(loginReq);
    }
}
