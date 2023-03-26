package com.example.stockapp2.dto.request;

import lombok.Data;

@Data
public class LoginReq {
    private String email;

    private String password;
}
