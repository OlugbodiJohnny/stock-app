package com.example.stockapp2.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class JwtResponse {
    private final String token;

    private final String type = "Bearer";

    private final Long id;

    private final String email;

    private final List<String> roles;
}
