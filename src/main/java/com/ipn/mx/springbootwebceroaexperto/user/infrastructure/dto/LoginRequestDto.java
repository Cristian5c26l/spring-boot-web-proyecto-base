package com.ipn.mx.springbootwebceroaexperto.user.infrastructure.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;// username
    private String password;
}
