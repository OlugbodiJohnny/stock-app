package com.example.stockapp2.exceptions;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class ApiBadRequestException extends  RuntimeException{
    public ApiBadRequestException(String message) {
        super(message);
    }
}
