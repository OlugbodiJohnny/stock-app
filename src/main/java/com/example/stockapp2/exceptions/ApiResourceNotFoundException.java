package com.example.stockapp2.exceptions;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class ApiResourceNotFoundException extends RuntimeException{

    public ApiResourceNotFoundException (String message) {
    }
}
