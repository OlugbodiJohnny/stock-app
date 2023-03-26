package com.example.stockapp2.services;

import com.example.stockapp2.dto.request.LoginReq;
import com.example.stockapp2.dto.request.RegisterUserReq;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> registerUser(RegisterUserReq registerUserReq);

    ResponseEntity<?> login(LoginReq loginReq);
}
