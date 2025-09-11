package com.ipn.mx.springbootwebceroaexperto.user.infrastructure.api.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequestDto {

    @Email
    private String email;// username
    private String password;
}
