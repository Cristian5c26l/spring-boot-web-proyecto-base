package com.ipn.mx.springbootwebceroaexperto.user.application.command.register;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserResponse {
    private String token;
}
