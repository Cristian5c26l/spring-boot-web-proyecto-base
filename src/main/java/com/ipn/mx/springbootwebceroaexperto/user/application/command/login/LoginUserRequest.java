package com.ipn.mx.springbootwebceroaexperto.user.application.command.login;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.Request;
import lombok.Data;

@Data
public class LoginUserRequest implements Request<LoginUserResponse> {
    private String email;
    private String password;
}
