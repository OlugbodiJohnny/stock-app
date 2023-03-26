package com.example.stockapp2.dto.request;

import com.example.stockapp2.validator.ValidPassword;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
public class RegisterUserReq {

    @NotNull(message = "Please enter a valid name")
    private String fullName;

    @NotNull(message = "Please enter a valid email")
    private String email;

    @NotNull(message = "Please enter a valid phone number")
    @ValidPassword(message = "password must not be less than 8 characters")
    private String password;


}
