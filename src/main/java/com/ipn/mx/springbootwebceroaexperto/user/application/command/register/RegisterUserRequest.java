package com.ipn.mx.springbootwebceroaexperto.user.application.command.register;

import com.ipn.mx.springbootwebceroaexperto.common.application.mediator.Request;
import lombok.Data;

@Data
public class RegisterUserRequest implements Request<RegisterUserResponse> {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
